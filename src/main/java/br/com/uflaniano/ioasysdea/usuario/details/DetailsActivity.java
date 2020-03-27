package br.com.uflaniano.ioasysdea.usuario.details;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

import br.com.uflaniano.ioasysdea.R;
import br.com.uflaniano.ioasysdea.rede.model.Enterprise;

public class DetailsActivity extends AppCompatActivity {

    public final static String EXTRA_ENTERPRISES = "extra_emterprises";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        Enterprise enterprise = getIntent().getParcelableExtra(EXTRA_ENTERPRISES);

        configLayout(enterprise);
    }

    private void configLayout(Enterprise enterprise){
        ImageView imageDetail;
        TextView textInfo;

        imageDetail = findViewById(R.id.imageDetail);
        textInfo = findViewById(R.id.textInfo);


        Glide.with(this)
                .load("http://empresas.ioasys.com.br" + enterprise.getPhoto())
                .placeholder(R.drawable.img_e_1_lista)
                .into(imageDetail);
        textInfo.setText(enterprise.getDescription());
    }
}