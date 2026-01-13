package de.hnu.data;

import java.util.List;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;

@Entity
@Table(name = "person")
public class Person {

    @Id
    private String id;

    private String name;
    private String gender;
    private Integer age;

    @Column(name = "profile_picture")
    private String profilePicture;

    @Column(name = "id_picture")
    private String idPicture;

    @Column(name = "license_picture")
    private String licensePicture;

    @Column(name = "home_city")
    private String homeCity;

    private String bio;

    @Column(name = "phone_number")
    private String phoneNumber;

    private String email;

    private String street;

    @Column(name = "postal_code")
    private String postalCode;

    // === Collections ===

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(
        name = "person_preferred_transmission",
        joinColumns = @JoinColumn(name = "person_id")
    )
    @Column(name = "preferred_transmission")
    private List<String> preferredTransmission;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(
        name = "person_preferred_car_type",
        joinColumns = @JoinColumn(name = "person_id")
    )
    @Column(name = "preferred_car_type")
    private List<String> preferredCarType;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(
        name = "person_preferred_music",
        joinColumns = @JoinColumn(name = "person_id")
    )
    @Column(name = "preferred_music")
    private List<String> preferredMusic;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(
        name = "person_languages",
        joinColumns = @JoinColumn(name = "person_id")
    )
    @Column(name = "language")
    private List<String> languages;

    @Column(name = "car_id")
    private String carId; // references Car.id

    @Column(name = "chatiness_level")
    private Integer chatinessLevel;

    @Column(name = "overall_km_covered")
    private Double overallKmCovered;

    public Person() {}

    // --- getters & setters ---

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }

    public Integer getAge() { return age; }
    public void setAge(Integer age) { this.age = age; }

    public String getProfilePicture() { return profilePicture; }
    public void setProfilePicture(String profilePicture) { this.profilePicture = profilePicture; }

    public String getIdPicture() { return idPicture; }
    public void setIdPicture(String idPicture) { this.idPicture = idPicture; }

    public String getLicensePicture() { return licensePicture; }
    public void setLicensePicture(String licensePicture) { this.licensePicture = licensePicture; }

    public String getHomeCity() { return homeCity; }
    public void setHomeCity(String homeCity) { this.homeCity = homeCity; }

    public String getBio() { return bio; }
    public void setBio(String bio) { this.bio = bio; }

    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getStreet() { return street; }
    public void setStreet(String street) { this.street = street; }

    public String getPostalCode() { return postalCode; }
    public void setPostalCode(String postalCode) { this.postalCode = postalCode; }

    public List<String> getPreferredTransmission() { return preferredTransmission; }
    public void setPreferredTransmission(List<String> preferredTransmission) { this.preferredTransmission = preferredTransmission; }

    public List<String> getPreferredCarType() { return preferredCarType; }
    public void setPreferredCarType(List<String> preferredCarType) { this.preferredCarType = preferredCarType; }

    public List<String> getPreferredMusic() { return preferredMusic; }
    public void setPreferredMusic(List<String> preferredMusic) { this.preferredMusic = preferredMusic; }

    public List<String> getLanguages() { return languages; }
    public void setLanguages(List<String> languages) { this.languages = languages; }

    public String getCarId() { return carId; }
    public void setCarId(String carId) { this.carId = carId; }

    public Integer getChatinessLevel() { return chatinessLevel; }
    public void setChatinessLevel(Integer chatinessLevel) { this.chatinessLevel = chatinessLevel; }

    public Double getOverallKmCovered() { return overallKmCovered; }
    public void setOverallKmCovered(Double overallKmCovered) { this.overallKmCovered = overallKmCovered; }
}