package controler;

import model.Hotel;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

/**
 * Created by TienDQ on 1/15/16.
 */
public class ParsingDiaChiSoControl extends ParsingControl {

    public ParsingDiaChiSoControl(String link) {
        super(link);
    }

    @Override
    public Hotel parseHotel() {
        Hotel hotel = new Hotel();

        try {
            Document doc = Jsoup.connect(mLink).get();

            // set name
            Element titleElement = doc.select("h1.detail_title").first();
            hotel.setName(titleElement.ownText());

            // set location's latitude
            Element latElement = doc.select("meta[property=place:location:latitude]").first();
            if (latElement != null){
                hotel.setLatitude(Double.parseDouble(latElement.attr("content")));
            }

            // set location's longitude
            Element longElement = doc.select("meta[property=place:location:longitude]").first();
            if (longElement != null){
                hotel.setLongitude(Double.parseDouble(longElement.attr("content")));
            }

            Element contactElements = doc.select("ul.contact_list_info").first();
            Elements contacts = contactElements.select("li");

            if (contacts != null && contacts.size() > 0) {
                for (Element contact: contacts){
                    // set address
                    Element address = contact.select("i.fa-map-marker").first();
                    if (address != null){
                        hotel.setAddress(contact.ownText());
                    }

                    // set phone
                    Element phone = contact.select("i.fa-phone").first();
                    if (phone != null){
                        hotel.setPhone(contact.ownText());
                    }

                    // set website
                    Element website = contact.select("i.fa-link").first();
                    if (website != null){
                        hotel.setWebsite(contact.ownText());
                    }

                    // set email
                    Element email = contact.select("i.fa-envelope-o").first();
                    if (email != null){
                        hotel.setEmail(contact.ownText());
                    }
                }
            }
        } catch (IOException e){
            e.printStackTrace();
        }

        System.out.println(hotel.toString());
        return hotel;
    }
}
