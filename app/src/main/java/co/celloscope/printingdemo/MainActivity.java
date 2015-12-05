package co.celloscope.printingdemo;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import java.io.File;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    public static final int PICK_PHOTO = 200;
    // Asset that contains html template
    private static final String SAVINGS_ACCOUNT_TEMPLATE = "savings_account.html";
    private HtmlHelper htmlHelper;

    private EditText account_nameEditText;
    private EditText account_noEditText;
    private EditText account_opening_dateEditText;
    private EditText account_typeEditText;
    private EditText agent_idEditText;
    private EditText agent_nameEditText;
    private EditText booth_addressEditText;
    private EditText districtEditText;
    private EditText id_noEditText;
    private EditText mobile_noEditText;
    private EditText nameEditText;
    private EditText photoEditText;
    private EditText print_dateEditText;
    private EditText sub_districtEditText;
    private EditText villageEditText;
    private EditText unionEditText;

    private String account_name;
    private String account_no;
    private String account_opening_date;
    private String account_type;
    private static final String agent_banking_logo = "file:///android_asset/agent_banking_logo.jpg";
    private String agent_id;
    private String agent_name;
    private String booth_address;
    private String district;
    private String id_no;
    private static final String logo = "file:///android_asset/logo.gif";
    private String mobile_no;
    private String name;
    private String photo;
    private String print_date;
    private static final String style_sheet = "file:///android_asset/styles.css";
    private String sub_district;
    private String village;
    private String union;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializeControl();
        setTestValue();
        htmlHelper = new HtmlHelper(this);

        findViewById(R.id.photoButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, PICK_PHOTO);
            }
        });

        findViewById(R.id.webViewPrintButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    setValue();
                    new WebViewPrint(MainActivity.this)
                            .print(getHtmlFile());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void initializeControl() {
        account_nameEditText = (EditText) findViewById(R.id.nameEditText);
        account_noEditText = (EditText) findViewById(R.id.nameEditText);
        account_opening_dateEditText = (EditText) findViewById(R.id.nameEditText);
        account_typeEditText = (EditText) findViewById(R.id.nameEditText);
        agent_idEditText = (EditText) findViewById(R.id.nameEditText);
        agent_nameEditText = (EditText) findViewById(R.id.nameEditText);
        booth_addressEditText = (EditText) findViewById(R.id.nameEditText);
        districtEditText = (EditText) findViewById(R.id.nameEditText);
        id_noEditText = (EditText) findViewById(R.id.nameEditText);
        mobile_noEditText = (EditText) findViewById(R.id.nameEditText);
        nameEditText = (EditText) findViewById(R.id.nameEditText);
        photoEditText = (EditText) findViewById(R.id.nameEditText);
        print_dateEditText = (EditText) findViewById(R.id.nameEditText);
        sub_districtEditText = (EditText) findViewById(R.id.nameEditText);
        villageEditText = (EditText) findViewById(R.id.villageEditText);
        unionEditText = (EditText) findViewById(R.id.nameEditText);
    }

    private void setTestValue() {
        account_nameEditText.setText("Unknown");
        account_noEditText.setText("Unknown");
        account_opening_dateEditText.setText("Unknown");
        account_typeEditText.setText("Unknown");
        agent_idEditText.setText("Unknown");
        agent_nameEditText.setText("Unknown");
        booth_addressEditText.setText("Unknown");
        districtEditText.setText("Unknown");
        id_noEditText.setText("Unknown");
        mobile_noEditText.setText("Unknown");
        nameEditText.setText("Unknown");
        photoEditText.setText("Unknown");
        print_dateEditText.setText("Unknown");
        sub_districtEditText.setText("Unknown");
        villageEditText.setText("Unknown");
        unionEditText.setText("Unknown");
    }

    private void setValue() {
        account_name = account_nameEditText.getText().toString();
        account_no = account_noEditText.getText().toString();
        account_opening_date = account_opening_dateEditText.getText().toString();
        account_type = account_typeEditText.getText().toString();
        agent_id = agent_idEditText.getText().toString();
        agent_name = agent_nameEditText.getText().toString();
        booth_address = booth_addressEditText.getText().toString();
        district = districtEditText.getText().toString();
        id_no = id_noEditText.getText().toString();
        mobile_no = mobile_noEditText.getText().toString();
        name = nameEditText.getText().toString();
        photo = photoEditText.getText().toString();
        print_date = print_dateEditText.getText().toString();
        sub_district = sub_districtEditText.getText().toString();
        village = villageEditText.getText().toString();
        union = unionEditText.getText().toString();
    }

    private File getHtmlFile() throws IOException {
        return FileHelper.createTempFileInExternalCacheDirectory(this, getHtml());
    }

    private String getHtml() throws IOException {
        String[] keys = {"#ACCOUNT_NAME", "#ACCOUNT_NO", "#ACCOUNT_OPENING_DATE", "#ACCOUNT_TYPE",
                "#AGENT_BANKING_LOGO", "#AGENT_ID", "#AGENT_NAME",
                "#BOOTH_ADDRESS", "#DISTRICT", "#ID_NO", "#LOGO", "#MOBILE_NO", "#NAME", "#PHOTO", "#PRINT_DATE",
                "#STYLE_SHEET", "#SUB_DISTRICT",
                "#VILLAGE", "#UNION",
        };

        String[] values = {account_name, account_no, account_opening_date, account_type,
                agent_banking_logo, agent_id, agent_name, booth_address,
                district, id_no, logo, mobile_no,
                name, photo, print_date, style_sheet,
                sub_district, village, union};
        return htmlHelper.getHtml(SAVINGS_ACCOUNT_TEMPLATE, keys, values);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            ImageView imageView = null;
            switch (requestCode) {
                case PICK_PHOTO:
                    imageView = (ImageView) findViewById(R.id.photoImageView);
                    FileHelper.copyFileToExternalCacheDir(this,
                            new File(FileHelper.getRealPathFromUri(this, data.getData())), "photo.jpg");
                    break;
            }
            if (imageView != null) {
                final Bitmap photo;
                try {
                    photo = BitmapHelper.getThumbnail(data.getData(), this);
                    imageView.setImageBitmap(photo);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }
}