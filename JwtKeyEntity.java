
import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Entity for storing JWT keys in database
 */
@Entity
@Table(name = "jwt_keys")
public class JwtKeyEntity {

    @Id
    @Column(name = "key_id", length = 50)
    private String keyId;

    @Column(name = "secret", nullable = false, length = 500)
    private String secret;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "expires_at")
    private LocalDateTime expiresAt;

    @Column(name = "is_active", nullable = false)
    private boolean active;

    @Column(name = "created_by", length = 100)
    private String createdBy;

    @Column(name = "rotation_reason", length = 255)
    private String rotationReason;

    @PrePersist
    protected void onCreate() {
        if (createdAt == null) {
            createdAt = LocalDateTime.now();
        }
    }

    // Getters and setters
    public String getKeyId() {
        return keyId;
    }

    public void setKeyId(String keyId) {
        this.keyId = keyId;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(LocalDateTime expiresAt) {
        this.expiresAt = expiresAt;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getRotationReason() {
        return rotationReason;
    }

    public void setRotationReason(String rotationReason) {
        this.rotationReason = rotationReason;
    }
}