package example.sample;

import org.bson.Document;

public class Email {
    private String personal;
    private String official;

    public Email(Builder builder) {
        this.personal = builder.personal;
        this.official = builder.official;
    }

    public String getPersonal() {
        return personal;
    }

    public void setPersonal(String personal) {
        this.personal = personal;
    }

    public String getOfficial() {
        return official;
    }

    public void setOfficial(String official) {
        this.official = official;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Email email = (Email) o;

        if (personal != null ? !personal.equals(email.personal) : email.personal != null) return false;
        return official != null ? official.equals(email.official) : email.official == null;
    }

    @Override
    public int hashCode() {
        int result = personal != null ? personal.hashCode() : 0;
        result = 31 * result + (official != null ? official.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Email{" +
                "personal='" + personal + '\'' +
                ", official='" + official + '\'' +
                '}';
    }

    public static class Builder {
        private String personal;
        private String official;

        public Builder(Document document) {
            this.personal = document.getString("personal");
            this.official = document.getString("official");

        }

        public Email build() {
            return new Email(this);
        }
    }
}
