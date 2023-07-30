package com.example.senportable;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.text.format.Formatter;
import android.view.MenuItem;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import android.Manifest;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener
{
    private DrawerLayout drawerLayout;

    private String ipAddress;
    private String imei;
    private String latitude;
    private String longitude;
    String ret;

    private String[] permissions;
    final private int REQUEST_CODE = 10001;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawer_layout);

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottombar);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener()
        {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item)
            {
                switch (item.getItemId())
                {
                    case R.id.nav_home:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();
                        break;

                    case R.id.nav_coordonnee:
                        CoordonneeFragment coordonneeFragment = new CoordonneeFragment();
                        fragmentHandler(coordonneeFragment, ret);
                        break;

                    case R.id.nav_map:
                        Intent intent = new Intent(getApplicationContext(), MapActivity.class);
                        intent.putExtra("lat", latitude);
                        intent.putExtra("lon", longitude);
                        startActivity(intent);
//                        Toast.makeText(getApplicationContext(), "sss", Toast.LENGTH_LONG).show();
                        break;

                    case R.id.nav_ip:
                        IPFragment ipFragment = new IPFragment();
                        fragmentHandler(ipFragment, ipAddress);
                        break;

                    case R.id.nav_imei:
                        IMEIFragment imeiFragment = new IMEIFragment();
                        fragmentHandler(imeiFragment, imei);
                        break;
                }

                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        if(savedInstanceState == null)
        {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();
            navigationView.setCheckedItem(R.id.nav_home);
        }

        // Status bar
        getWindow().setStatusBarColor(ContextCompat.getColor(getApplicationContext(), R.color.transparent_blue));

        // Inialiser les attrubuts;
        ipAddress = getIPAddress();
        imei = getIMEI();
        ret = getCoordonnee();

        // Verification des permissions
        permissions = new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.READ_PHONE_STATE, Manifest.permission.ACCESS_WIFI_STATE};
    }

    @Override
    protected void onStart()
    {
        super.onStart();
        if(!hasPermissions(MainActivity.this, permissions))
        {
            ActivityCompat.requestPermissions(MainActivity.this, permissions, REQUEST_CODE);
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.nav_home:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();
                break;

            case R.id.nav_coordonnee:
                CoordonneeFragment coordonneeFragment = new CoordonneeFragment();
                fragmentHandler(coordonneeFragment, ret);
                break;

            case R.id.nav_map:
                Intent intent = new Intent(getApplicationContext(), MapActivity.class);
                intent.putExtra("lat", latitude);
                intent.putExtra("lon", longitude);
                startActivity(intent);
//                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new MapFragment()).commit();
                break;

            case R.id.nav_ip:
                IPFragment ipFragment = new IPFragment();
                fragmentHandler(ipFragment, ipAddress);
                break;

            case R.id.nav_imei:
                IMEIFragment imeiFragment = new IMEIFragment();
                fragmentHandler(imeiFragment, imei);
                break;

            case R.id.nav_historic:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HistoricFragment()).commit();
                break;

            case R.id.nav_setting:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new SettingFragment()).commit();
                break;

            case R.id.nav_exit:
                Toast.makeText(getApplicationContext(), "Quitter", Toast.LENGTH_LONG).show();
                finish();
                System.exit(0);
                break;
        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed()
    {
        if(drawerLayout.isDrawerOpen(GravityCompat.START))
        {
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else
        {
            super.onBackPressed();
        }
    }

    void fragmentHandler(Fragment fragment, String data)
    {
        Bundle b = new Bundle();
        b.putString("data", data);
        fragment.setArguments(b);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();
    }

    private String getIPAddress()
    {
        WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);

        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_WIFI_STATE) == PackageManager.PERMISSION_GRANTED)
        {
            String ipAddress = Formatter.formatIpAddress(wifiManager.getConnectionInfo().getIpAddress());
            return ipAddress;
        }
        else
        {
            // Demander la permission
            requestPermissions(new String[] {Manifest.permission.ACCESS_WIFI_STATE}, REQUEST_CODE);

            return "Activez la permission au niveau de vos parametres";
        }
    }

    private String getIMEI()
    {
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED)
        {
            TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);


            // La fontion getDeviceId() et getImei() font caché mon telephone mais marche sur l'elemulateur blueStack
            // C'est pourquoi je l'ai mis en dur
            String imei = telephonyManager.getDeviceId();

//            String imei = "352288962277203";

            return imei;
        }
        else
        {
            // Demander la permission
            requestPermissions(new String[] {Manifest.permission.READ_PHONE_STATE}, REQUEST_CODE);

            return "Activez la permission au niveau de vos parametres";
        }
    }

    private String getCoordonnee()
    {
        FusedLocationProviderClient fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        // Test for the permission
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)
        {
            fusedLocationProviderClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>()
            {
                @Override
                public void onSuccess(Location location)
                {
                    if(location != null)
                    {
                        Geocoder geocoder = new Geocoder(MainActivity.this, Locale.getDefault());

                        try
                        {
                            List<Address> data =  geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
//
//                            String latitude = String.valueOf(data.get(0).getLatitude());
//                            String longitude = String.valueOf(data.get(0).getLongitude());

                            ret = data.get(0).getLatitude() + ";" +
                                    data.get(0).getLongitude() + ";" +
                                    data.get(0).getCountryName() + ";" +
                                    data.get(0).getLocality() + ";" +
                                    data.get(0).getAddressLine(0);

                            latitude = String.valueOf(data.get(0).getLatitude());
                            longitude = String.valueOf(data.get(0).getLongitude());

                            Toast.makeText(getApplicationContext(), longitude + "/" + latitude, Toast.LENGTH_LONG).show();
                        }
                        catch (IOException e)
                        {
                            Toast.makeText(getApplicationContext(), "Erreur", Toast.LENGTH_LONG).show();
                            ret = "Exp";
                        }
                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(), "Is null", Toast.LENGTH_LONG).show();
                        ret = "Null";
                    }
                }
            });
        }
        else
        {
            requestPermissions(new String[] {Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE);
            ret = "Dined";
        }

        return ret;
    }

    private boolean hasPermissions(Context context, String... permission)
    {
        if(context != null && permissions != null)
        {
            for(String perm: permissions)
            {
                if(ActivityCompat.checkSelfPermission(context, perm) != PackageManager.PERMISSION_GRANTED)
                {
                    return false;
                }
            }
        }

        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQUEST_CODE)
        {
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                Toast.makeText(this, "La localisation est autorisee", Toast.LENGTH_SHORT).show();
            }
            else
            {
                Toast.makeText(this, "La localisation est interdite", Toast.LENGTH_SHORT).show();
            }

            if(grantResults[1] == PackageManager.PERMISSION_GRANTED)
            {
                Toast.makeText(this, "L'usage des donnees du telephone est autorisee", Toast.LENGTH_SHORT).show();
            }
            else
            {
                Toast.makeText(this, "L'usage des donnees du telephone est interdite", Toast.LENGTH_SHORT).show();
            }

            if(grantResults[2] == PackageManager.PERMISSION_GRANTED)
            {
                Toast.makeText(this, "L'usage du WIFI est autorisee", Toast.LENGTH_SHORT).show();
            }
            else
            {
                Toast.makeText(this, "L'usage du WIFI est interdite", Toast.LENGTH_SHORT).show();
            }
        }
    }
}

// new AlertDialog.Builder(getActivity().getApplicationContext()).setTitle("Permission").setMessage("Cette permisson est neccessaire a l'application!!").setPositiveButton("ok", new DialogInterface.OnClickListener()
//         {
//@Override
//public void onClick(DialogInterface dialog, int which)
//        {
//
//        }
//        }).setNegativeButton("Annuler", new DialogInterface.OnClickListener()
//        {
//@Override
//public void onClick(DialogInterface dialog, int which)
//        {
//        dialog.dismiss();
