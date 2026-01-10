package de.hnu.domain;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("person")
public class Person {
    @Id
    private String id;

    private String name;
    private String gender; // keep String for now (enum later)
    private Integer age;

    private String profilePicture; // url or base64 later
    private String idPicture;
    private String licensePicture;

    private String homeCity;
    private String bio;
    private String phoneNumber;
    private String email;

    private String street;
    private String postalCode;

    private List<String> preferredTransmission;
    private List<String> preferredCarType;
    private List<String> preferredMusic;
    private String carId; // references Car.id
    private List<String> languages;

    private Integer chatinessLevel;
    private Double overallKmCovered;

    public Person() {}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    public String getIdPicture() {
        return idPicture;
    }

    public void setIdPicture(String idPicture) {
        this.idPicture = idPicture;
    }

    public String getLicensePicture() {
        return licensePicture;
    }

    public void setLicensePicture(String licensePicture) {
        this.licensePicture = licensePicture;
    }

    public String getHomeCity() {
        return homeCity;
    }

    public void setHomeCity(String homeCity) {
        this.homeCity = homeCity;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public List<String> getPreferredTransmission() {
        return preferredTransmission;
    }

    public void setPreferredTransmission(List<String> preferredTransmission) {
        this.preferredTransmission = preferredTransmission;
    }

    public List<String> getPreferredCarType() {
        return preferredCarType;
    }

    public void setPreferredCarType(List<String> preferredCarType) {
        this.preferredCarType = preferredCarType;
    }

    public List<String> getPreferredMusic() {
        return preferredMusic;
    }

    public void setPreferredMusic(List<String> preferredMusic) {
        this.preferredMusic = preferredMusic;
    }

    public String getCarId() {
        return carId;
    }

    public void setCarId(String carId) {
        this.carId = carId;
    }

    public List<String> getLanguages() {
        return languages;
    }

    public void setLanguages(List<String> languages) {
        this.languages = languages;
    }

    public Integer getChatinessLevel() {
        return chatinessLevel;
    }

    public void setChatinessLevel(Integer chatinessLevel) {
        this.chatinessLevel = chatinessLevel;
    }

    public Double getOverallKmCovered() {
        return overallKmCovered;
    }

    public void setOverallKmCovered(Double overallKmCovered) {
        this.overallKmCovered = overallKmCovered;
    }
}