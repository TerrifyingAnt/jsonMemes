package jg.com.jsonmemes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    TextView textView;
    Button button;
    EditText getId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.textView);
        button = findViewById(R.id.button);
        getId = findViewById(R.id.getId);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(getId.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(), "Введи id", Toast.LENGTH_SHORT).show();
                    return;
                }
                else {
                    if(Integer.valueOf(getId.getText().toString()) > 0 && Integer.valueOf(getId.getText().toString()) < 101)
                    NetworkService.getInstance()
                            .getJSONApi()
                            .getPostWithID(Integer.valueOf(getId.getText().toString()))
                            .enqueue(new Callback<Post>() {
                                @Override
                                public void onResponse(@NonNull Call<Post> call, @NonNull Response<Post> response) {
                                    Post post = response.body();

                                    textView.setText("");
                                    textView.append(post.getId() + "\n");
                                    textView.append(post.getUserId() + "\n");
                                    textView.append(post.getTitle() + "\n");
                                    textView.append(post.getBody() + "\n");
                                }

                                @Override
                                public void onFailure(@NonNull Call<Post> call, @NonNull Throwable t) {

                                    textView.append("Error occurred while getting request!");
                                    t.printStackTrace();
                                }
                            });
                }
            }
        });

    }
}