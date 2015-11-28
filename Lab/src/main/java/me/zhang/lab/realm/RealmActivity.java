package me.zhang.lab.realm;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;
import me.zhang.lab.R;

public class RealmActivity extends AppCompatActivity {

    private Realm realm;
    private ListView countryList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_realm);

        countryList = (ListView) findViewById(R.id.lv_countries);

        realm = Realm.getInstance(
                new RealmConfiguration.Builder(this).name(System.currentTimeMillis() + ".realm").build()
        );
        realm.beginTransaction();

        // Create an object
        Country country = realm.createObject(Country.class);
        // Set its fields
        country.setName("Norway");
        country.setPopulation(5165800);
        country.setCode("NO");
        realm.commitTransaction();

        // Create the object
        Country country2 = new Country();
        country2.setName("Russia");
        country2.setPopulation(146430430);
        country2.setCode("RU");

        realm.beginTransaction();
        Country copyOfCountry2 = realm.copyToRealm(country2);
        realm.commitTransaction();
    }

    @Override
    protected void onStart() {
        super.onStart();
        RealmResults<Country> results = realm.where(Country.class).findAll();
        List<String> countries = new ArrayList<>();
        for (Country country : results) {
            countries.add(country.getName());
        }
        countryList.setAdapter(
                new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, countries)
        );
    }

}
