package org.baat.gqlapi.transfer;

public class User {

    private Long id;
    private String email;
    private String fullName;
    private String avatarUrl;

    public User() {
    }

    public User(final Long id, final String email, final String fullName, final String avatarUrl) {
        this.id = id;
        this.email = email;
        this.fullName = fullName;
        this.avatarUrl = avatarUrl;
    }

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(final String email) {
        this.email = email;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(final String fullName) {
        this.fullName = fullName;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(final String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        final User user = (User) o;

        if (id != null ? !id.equals(user.id) : user.id != null)
            return false;
        if (email != null ? !email.equals(user.email) : user.email != null)
            return false;
        if (fullName != null ? !fullName.equals(user.fullName) : user.fullName != null)
            return false;
        return avatarUrl != null ? avatarUrl.equals(user.avatarUrl) : user.avatarUrl == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (fullName != null ? fullName.hashCode() : 0);
        result = 31 * result + (avatarUrl != null ? avatarUrl.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("User{");
        sb.append("id=").append(id);
        sb.append(", email='").append(email).append('\'');
        sb.append(", fullName='").append(fullName).append('\'');
        sb.append(", avatarUrl='").append(avatarUrl).append('\'');
        sb.append('}');
        return sb.toString();
    }

}
