package example.sample;

import org.bson.Document;

public class PhoneContact {
    private String personal;
    private String work;

    public PhoneContact(Builder builder) {
        this.personal = builder.personal;
        this.work = builder.work;
    }

    public String getPersonal() {
        return personal;
    }

    public void setPersonal(String personal) {
        this.personal = personal;
    }

    public String getWork() {
        return work;
    }

    public void setWork(String work) {
        this.work = work;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PhoneContact that = (PhoneContact) o;

        if (!personal.equals(that.personal)) return false;
        return work != null ? work.equals(that.work) : that.work == null;
    }

    @Override
    public int hashCode() {
        int result = personal.hashCode();
        result = 31 * result + (work != null ? work.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "PhoneContact{" +
                "personal=" + personal +
                ", work=" + work +
                '}';
    }

    public static class Builder {
        private String personal;
        private String work;

        public Builder(Document document) {
            this.personal = document.getString("personal");
            this.work = document.getString("work");

        }

        public PhoneContact build() {
            return new PhoneContact(this);
        }
    }
}
