package com.example.chemist;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity2 extends AppCompatActivity {

    private static final String TAG = "MyActivity";

    FirebaseFirestore firestore;
    protected  final int RESULT_SPEECH = 1;
    protected  final int RESULT_SPEECH2 = 2;
    public ImageButton btnSpeak;
    public ImageButton btnSpeak2;
    public Button btnfetch;
    public Button btnreacfetch;
    public Button btnimagefetch;
    private EditText tvText; //for first input element
    private EditText tvText2; //for second input element
    private TextView tvFinal; //textView for symbol
    private TextView tvReaction; //textView for reaction
    private TextView tvImage; //textView for image
    private ProgressBar progressBar; //progressbar in symbol
    private ProgressBar progressBar2; // progressbar in reaction
    private ImageView imageView; //imageView to show molecular structure

    private ImageButton ibInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        BottomNavigationView bottom_NavigationView = findViewById(R.id.bottom_NavigationView);
        bottom_NavigationView.setSelectedItemId(R.id.c_reaction);

        bottom_NavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.c_reaction) {
                return true;
            } else if (itemId == R.id.c_table) {
                startActivity(new Intent(getApplicationContext(), MainActivity3.class));
                overridePendingTransition(R.anim.slidein_right, R.anim.slideout_left);
                finish();
                return true;
            } else if (itemId == R.id.c_about) {
                startActivity(new Intent(getApplicationContext(), MainActivity4.class));
                overridePendingTransition(R.anim.slidein_right, R.anim.slideout_left);
                finish();
                return true;
            }
            return false;
        });

        //hide the actionbar
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        tvText=findViewById(R.id.tvText);
        tvText2=findViewById(R.id.tvText2);
        btnSpeak=findViewById(R.id.btnSpeak);
        btnSpeak2=findViewById(R.id.btnSpeak2);
        btnfetch=findViewById(R.id.btnfetch);
        btnreacfetch=findViewById(R.id.buttonreacfetch);
        btnimagefetch=findViewById(R.id.btnimagefetch);
        tvFinal=findViewById(R.id.tvFinal);
        tvReaction=findViewById(R.id.tvReaction);
        tvImage=findViewById(R.id.tvImage);
        firestore = FirebaseFirestore.getInstance();
        progressBar = findViewById(R.id.progressBar);
        progressBar2 = findViewById(R.id.progressBar2);
        imageView = findViewById(R.id.imageView);
        ibInfo = findViewById(R.id.ibInfo);

        ibInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity2.this, MainActivity5.class);
                startActivity(intent);
            }
        });

        //fetch reaction button
        btnreacfetch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reactionFetch();
            }
        });

        //fetch symbol button
        btnfetch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fetch();
            }
        });

        //fetch image button
        btnimagefetch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                imageFetch();
            }
        });

        //first mic button
        btnSpeak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE,"en-US");

                try {
                    startActivityForResult(intent,RESULT_SPEECH);
                    tvText.setText("");
                }catch (ActivityNotFoundException e){
                    Toast.makeText(getApplicationContext(),"Your device doesn't support Chemist",Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
        });

        //second mic button
        btnSpeak2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE,"en-Us");

                try {
                    startActivityForResult(intent,RESULT_SPEECH2);
                    tvText2.setText("");
                } catch (ActivityNotFoundException e) {
                    Toast.makeText(getApplicationContext(), "Your device doesn't support Chemist", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode){
            case RESULT_SPEECH:
                if (resultCode==RESULT_OK && data!=null){
                    ArrayList<String> text=data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    tvText.setText(text.get(0));
                }
                break;
        }

        switch (requestCode) {
            case RESULT_SPEECH2:
                if (resultCode==RESULT_OK && data!=null) {
                    ArrayList<String> text=data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    tvText2.setText(text.get(0));
                }
                break;
        }
    }

    private void fetch() {
        // Show the progress bar
        progressBar.setVisibility(View.VISIBLE);

        String element1 = tvText.getText().toString().trim();
        String element2 = tvText2.getText().toString().trim();

        firestore.collection("ChemicalElements")
                .whereIn("name", Arrays.asList(element1, element2))
                .get()
                .addOnCompleteListener(task -> {
                    // Hide the progress bar
                    progressBar.setVisibility(View.INVISIBLE);

                    if (task.isSuccessful()) {
                        StringBuilder sb = new StringBuilder();
                        for (QueryDocumentSnapshot document : task.getResult()) {

                            Chemical chemical = document.toObject(Chemical.class);
                            sb.append(chemical.toString()).append("\n");
                        }
                        if (sb.length() > 0) {
                            tvFinal.setText(sb.toString());
                        } else {
                            tvFinal.setText("No symbols found for the given names");
                        }
                    } else {
                        Log.d(TAG, "Error getting documents: ", task.getException());
                        tvFinal.setText("Error fetching symbols");
                    }
                });
    }

    private void reactionFetch() {
        // Show the progress bar
        progressBar2.setVisibility(View.VISIBLE);

        String e1 = tvText.getText().toString().trim();
        String e2 = tvText2.getText().toString().trim();

        firestore.collection("ChemicalElements")
                .whereIn("firstElement", Arrays.asList(e1, e2))
                .whereIn("secondElement", Arrays.asList(e1, e2))
                .get()
                .addOnCompleteListener(task -> {
                    // Hide the progress bar
                    progressBar2.setVisibility(View.INVISIBLE);

                    if (task.isSuccessful()) {
                        StringBuilder st = new StringBuilder();
                        for (QueryDocumentSnapshot document : task.getResult()) {

                            Reaction reaction = document.toObject(Reaction.class);
                            st.append(reaction.toString()).append("\n");
                        }
                        if (st.length() > 0) {
                            tvReaction.setText(st.toString());
                        } else {
                            tvReaction.setText("No reactions found for the given names");
                        }
                    } else {
                        Log.d(TAG, "Error getting documents: ", task.getException());
                        tvReaction.setText("Error fetching reactions");
                    }
                });
    }

    private void imageFetch() {
        // Show the progress bar
        progressBar2.setVisibility(View.VISIBLE);

        String e1 = tvText.getText().toString().trim();
        String e2 = tvText2.getText().toString().trim();

        firestore.collection("ChemicalElements")
                .whereIn("firstElement", Arrays.asList(e1, e2))
                .whereIn("secondElement", Arrays.asList(e1, e2))
                .get()
                .addOnCompleteListener(task -> {
                    // Hide the progress bar
                    progressBar2.setVisibility(View.INVISIBLE);

                    if (task.isSuccessful()) {
                        StringBuilder st = new StringBuilder();
                        for (QueryDocumentSnapshot document : task.getResult()) {

                            Img image = document.toObject(Img.class);
                            st.append(image.getImages()).append("\n");
                        }
                        if (st.length() > 0) {
                            // Load the first image URL from the StringBuilder
                            String imageUrl = st.toString().trim();
                            loadImageWithGlide(imageUrl);
                        } else {
                            tvImage.setText("No image found for the given reaction");
                        }
                    } else {
                        Log.d(TAG, "Error getting documents: ", task.getException());
                        tvImage.setText("Error fetching image");
                    }
                });
    }

    private void loadImageWithGlide(String imageUrl) {
        Glide.with(MainActivity2.this)
                .load(imageUrl)
                .placeholder(R.drawable.image_icon_chemist)
                .into(imageView);
    }

}
