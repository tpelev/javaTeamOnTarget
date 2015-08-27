package model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the places database table.
 * 
 */
@Entity
@Table(name="places")
@NamedQueries({
    @NamedQuery(name = "Place.findAll", query = "SELECT p FROM Place p"),
    @NamedQuery(name = "Place.findById", query = "SELECT p FROM Place p WHERE p.id = :id"),
    @NamedQuery(name = "Place.findByPlacesName", query = "SELECT p FROM Place p WHERE p.placesName = :placesName")
})
public class Place implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	@Column(name="PLACES_NAME")
	private String placesName;

	//bi-directional many-to-one association to Advertisement
	@OneToMany(mappedBy="place", fetch=FetchType.EAGER)
	private List<Advertisement> advertisements;

	public Place() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPlacesName() {
		return this.placesName;
	}

	public void setPlacesName(String placesName) {
		this.placesName = placesName;
	}

	public List<Advertisement> getAdvertisements() {
		return this.advertisements;
	}

	public void setAdvertisements(List<Advertisement> advertisements) {
		this.advertisements = advertisements;
	}

	public Advertisement addAdvertisement(Advertisement advertisement) {
		getAdvertisements().add(advertisement);
		advertisement.setPlace(this);

		return advertisement;
	}

	public Advertisement removeAdvertisement(Advertisement advertisement) {
		getAdvertisements().remove(advertisement);
		advertisement.setPlace(null);

		return advertisement;
	}

}