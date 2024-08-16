@RestController
@RequestMapping("/api/v1")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequest request) {
        User user = userService.registerUser(request.getUsername(), request.getEmail(), request.getPassword());
        userService.generateAndSendOtp(request.getEmail());
        return ResponseEntity.ok("User registered successfully! OTP sent to email.");
    }

    @PostMapping("/verify-otp")
    public ResponseEntity<String> verifyOtp(@RequestBody OtpRequest request) {
        boolean isValid = userService.verifyOtp(request.getEmail(), request.getOtpCode());
        return isValid ? ResponseEntity.ok("OTP verified successfully!")
                       : ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid or expired OTP.");
    }
}
