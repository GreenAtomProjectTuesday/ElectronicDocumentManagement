package electonic.document.management.config.filter;

public interface FilterConstant {
    String SECRET = "SecretKeyToGenJWTs";
    long EXPIRATION_TIME = 864_000_000; // 10 days
    String COOKIE_NAME = "Authorization";
}
