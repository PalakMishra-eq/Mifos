public interface OtpRepository extends JpaRepository<Otp, Long> {
    Optional<Otp> findByEmailAndOtpCode(String email, String otpCode);
    void deleteByEmail(String email);  // Optional, for cleanup
}
