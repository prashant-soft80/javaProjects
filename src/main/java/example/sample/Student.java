package example.sample;

import org.bson.Document;

public class Student {
    private String firstName;
    private String lastName;
    private Integer age;
    private Address address;
    private PhoneContact phoneContact;
    private Email email;

    private Student(Builder builder) {
        this.firstName = builder.firstName;
        this.lastName = builder.lastName;
        this.age = builder.age;
        this.address = builder.address;
        this.phoneContact = builder.phoneContact;
        this.email = builder.email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public PhoneContact getPhoneContact() {
        return phoneContact;
    }

    public void setPhoneContact(PhoneContact phoneContact) {
        this.phoneContact = phoneContact;
    }

    public Email getEmail() {
        return email;
    }

    public void setEmail(Email email) {
        this.email = email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Student student = (Student) o;

        if (firstName != null ? !firstName.equals(student.firstName) : student.firstName != null) return false;
        if (!lastName.equals(student.lastName)) return false;
        if (!age.equals(student.age)) return false;
        if (address != null ? !address.equals(student.address) : student.address != null) return false;
        if (phoneContact != null ? !phoneContact.equals(student.phoneContact) : student.phoneContact != null)
            return false;
        return email != null ? email.equals(student.email) : student.email == null;
    }

    @Override
    public int hashCode() {
        int result = firstName != null ? firstName.hashCode() : 0;
        result = 31 * result + lastName.hashCode();
        result = 31 * result + age.hashCode();
        result = 31 * result + (address != null ? address.hashCode() : 0);
        result = 31 * result + (phoneContact != null ? phoneContact.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Student{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", age=" + age +
                ", address=" + address +
                ", phoneContact=" + phoneContact +
                ", email=" + email +
                '}';
    }

    public static class Builder {
        private String firstName;
        private String lastName;
        private Integer age;
        private Address address;
        private PhoneContact phoneContact;
        private Email email;

        public Builder(Document document) {
            this.firstName = document.getString("firstName");
            this.lastName = document.getString("lastName");
            this.age = document.getInteger("age");
        }

        public Builder address(Document document) {
            this.address = new Address.Builder((Document) document.get("address")).build();
            return this;
        }

        public Builder phoneContact(Document document) {
            this.phoneContact = new PhoneContact.Builder((Document) document.get("phoneContacts")).build();
            return this;
        }

        public Builder email(Document document) {
            this.email = new Email.Builder((Document) document.get("emails")).build();
            return this;
        }

        public Student build() {
            return new Student(this);
        }
    }
}
