
import org.jasypt.encryption.StringEncryptor;
import org.mockito.Mockito;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

/**
 * Test configuration for mocking encryption services
 * This allows tests to run without requiring actual Jasypt passwords
 */
@TestConfiguration
@Profile("integration-test")
public class TestEncryptionConfig {

    /**
     * Mock StringEncryptor that returns predictable values for testing
     */
    @Bean
    @Primary
    public StringEncryptor mockStringEncryptor() {
        StringEncryptor mockEncryptor = Mockito.mock(StringEncryptor.class);
        
        // Mock encrypt to return a predictable encrypted string
        Mockito.when(mockEncryptor.encrypt(Mockito.anyString()))
               .thenAnswer(invocation -> "MOCK_ENCRYPTED_" + invocation.getArgument(0));
        
        // Mock decrypt to return the original string (removing the mock prefix)
        Mockito.when(mockEncryptor.decrypt(Mockito.anyString()))
               .thenAnswer(invocation -> {
                   String encrypted = invocation.getArgument(0);
                   if (encrypted.startsWith("MOCK_ENCRYPTED_")) {
                       return encrypted.substring("MOCK_ENCRYPTED_".length());
                   }
                   // For ENC() wrapped values, just return a mock decrypted value
                   if (encrypted.startsWith("ENC(") && encrypted.endsWith(")")) {
                       return "MOCK_DECRYPTED_VALUE";
                   }
                   return "MOCK_DECRYPTED_" + encrypted;
               });
        
        return mockEncryptor;
    }
    
    /**
     * Mock EncryptionService using Mockito that doesn't require Jasypt password
     */
    @Bean
    @Primary
    public EncryptionService mockEncryptionService() {
        EncryptionService mockService = Mockito.mock(EncryptionService.class);
        
        // Mock encrypt method
        Mockito.when(mockService.encrypt(Mockito.anyString()))
               .thenAnswer(invocation -> {
                   String plainText = invocation.getArgument(0);
                   if (plainText == null) {
                       return null;
                   }
                   return "MOCK_ENCRYPTED_" + plainText;
               });
        
        // Mock decrypt method
        Mockito.when(mockService.decrypt(Mockito.anyString()))
               .thenAnswer(invocation -> {
                   String encryptedText = invocation.getArgument(0);
                   if (encryptedText == null) {
                       return null;
                   }
                   if (encryptedText.startsWith("MOCK_ENCRYPTED_")) {
                       return encryptedText.substring("MOCK_ENCRYPTED_".length());
                   }
                   // Handle ENC() wrapped values
                   if (encryptedText.startsWith("ENC(") && encryptedText.endsWith(")")) {
                       return "MOCK_DECRYPTED_VALUE";
                   }
                   return "MOCK_DECRYPTED_" + encryptedText;
               });
        
        return mockService;
    }
}