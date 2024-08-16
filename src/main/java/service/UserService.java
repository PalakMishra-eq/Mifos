@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OtpRepository otpRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private SendGridService sendGridService;

    public User registerUser(String username, String email, String password) {
        String hashedPassword = passwordEncoder.encode(password);
        User user = new User(username, email, hashedPassword);
        return userRepository.save(user);
    }

    public void generateAndSendOtp(String email) {
        String otpCode = String.valueOf(new Random().nextInt(999999));
        Otp otp = new Otp(email, otpCode, LocalDateTime.now().plusMinutes(10));
        otpRepository.save(otp);
        sendGridService.sendOtpEmail(email, otpCode);
    }

    public boolean verifyOtp(String email, String otpCode) {
        return otpRepository.findByEmailAndOtpCode(email, otpCode)
                .filter(otp -> otp.getExpiryTime().isAfter(LocalDateTime.now()))
                .isPresent();
    }
}
