package com.phonegap.wordpowerswahili;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class CategoryListActivity extends AppCompatActivity implements View.OnClickListener {

    Button catBtnGreetings, catBtnExprs, catBtnNumber, catBtnTime, catBtnDay, catBtnMonth, catBtnSeason, catBtnTransport, catBtnPeople, catBtnWorker, catBtnCloth, catBtnFood, catBtnWeight, catBtnAnimalDom, catBtnAnimalWild, catBtnPest, catBtnBody, catBtnDirection, catBtnWord, catBtnIllness, catBtnSign, catBtnConstruction, catBtnStationaries, catBtnSubject, catBtnContinets, catBtn, catBtnCommonItems, catBtnColor, catBtnMath, catBtnAboutUs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        catBtnGreetings = (Button) findViewById(R.id.catBtnGreetings);
        catBtnExprs = (Button) findViewById(R.id.catBtnExprs);
        catBtnNumber = (Button) findViewById(R.id.catBtnNumber);
        catBtnTime = (Button) findViewById(R.id.catBtnTime);
        catBtnDay = (Button) findViewById(R.id.catBtnDay);
        catBtnMonth = (Button) findViewById(R.id.catBtnMonth);
        catBtnSeason = (Button) findViewById(R.id.catBtnSeason);
        catBtnTransport = (Button) findViewById(R.id.catBtnTransport);
        catBtnPeople = (Button) findViewById(R.id.catBtnPeople);
        catBtnWorker = (Button) findViewById(R.id.catBtnWorker);
        catBtnCloth = (Button) findViewById(R.id.catBtnCloth);
        catBtnFood = (Button) findViewById(R.id.catBtnFood);
        catBtnWeight = (Button) findViewById(R.id.catBtnWeight);
        catBtnAnimalDom = (Button) findViewById(R.id.catBtnAnimalDom);
        catBtnAnimalWild = (Button) findViewById(R.id.catBtnAnimalWild);
        catBtnPest = (Button) findViewById(R.id.catBtnPest);
        catBtnBody = (Button) findViewById(R.id.catBtnBody);
        catBtnDirection = (Button) findViewById(R.id.catBtnDirection);
        catBtnWord = (Button) findViewById(R.id.catBtnWord);
        catBtnIllness = (Button) findViewById(R.id.catBtnIllness);
        catBtnSign = (Button) findViewById(R.id.catBtnSign);
        catBtnConstruction = (Button) findViewById(R.id.catBtnConstruction);
        catBtnStationaries = (Button) findViewById(R.id.catBtnStationaries);
        catBtnSubject = (Button) findViewById(R.id.catBtnSubject);
        catBtnContinets = (Button) findViewById(R.id.catBtnContinents);
        catBtnCommonItems = (Button) findViewById(R.id.catBtnCommonItems);
        catBtnColor = (Button) findViewById(R.id.catBtnColor);
        catBtnMath = (Button) findViewById(R.id.catBtnMath);
        catBtnAboutUs = (Button) findViewById(R.id.catBtnAboutUs);

        catBtnGreetings.setOnClickListener(this);
        catBtnExprs.setOnClickListener(this);
        catBtnNumber.setOnClickListener(this);
        catBtnTime.setOnClickListener(this);
        catBtnDay.setOnClickListener(this);
        catBtnMonth.setOnClickListener(this);
        catBtnSeason.setOnClickListener(this);
        catBtnTransport.setOnClickListener(this);
        catBtnPeople.setOnClickListener(this);
        catBtnWorker.setOnClickListener(this);
        catBtnCloth.setOnClickListener(this);
        catBtnFood.setOnClickListener(this);
        catBtnWeight.setOnClickListener(this);
        catBtnAnimalDom.setOnClickListener(this);
        catBtnAnimalWild.setOnClickListener(this);
        catBtnPest.setOnClickListener(this);
        catBtnBody.setOnClickListener(this);
        catBtnDirection.setOnClickListener(this);
        catBtnWord.setOnClickListener(this);
        catBtnIllness.setOnClickListener(this);
        catBtnSign.setOnClickListener(this);
        catBtnConstruction.setOnClickListener(this);
        catBtnStationaries.setOnClickListener(this);
        catBtnSubject.setOnClickListener(this);
        catBtnContinets.setOnClickListener(this);
        catBtnCommonItems.setOnClickListener(this);
        catBtnColor.setOnClickListener(this);
        catBtnMath.setOnClickListener(this);
        catBtnAboutUs.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        Bundle IDbundle = new Bundle();
        Intent intentItemList = new Intent(getApplicationContext(), ItemListActivity.class);

        switch (v.getId()) {
            case R.id.catBtnGreetings:
                IDbundle.putString("catName", "catName");
                IDbundle.putString("catID", "catID");
                intentItemList.putExtras(IDbundle);
                startActivity(intentItemList);
                break;
            case R.id.catBtnExprs:
                IDbundle.putString("catName", "catName");
                IDbundle.putString("catID", "catID");
                intentItemList.putExtras(IDbundle);
                startActivity(intentItemList);
            case R.id.catBtnNumber:
                IDbundle.putString("catName", "catName");
                IDbundle.putString("catID", "catID");
                intentItemList.putExtras(IDbundle);
                startActivity(intentItemList);
            case R.id.catBtnTime:
                IDbundle.putString("catName", "catName");
                IDbundle.putString("catID", "catID");
                intentItemList.putExtras(IDbundle);
                startActivity(intentItemList);
            case R.id.catBtnDay:
                IDbundle.putString("catName", "catName");
                IDbundle.putString("catID", "catID");
                intentItemList.putExtras(IDbundle);
                startActivity(intentItemList);
            case R.id.catBtnMonth:
                IDbundle.putString("catName", "catName");
                IDbundle.putString("catID", "catID");
                intentItemList.putExtras(IDbundle);
                startActivity(intentItemList);
            case R.id.catBtnSeason:
                IDbundle.putString("catName", "catName");
                IDbundle.putString("catID", "catID");
                intentItemList.putExtras(IDbundle);
                startActivity(intentItemList);
            case R.id.catBtnTransport:
                IDbundle.putString("catName", "catName");
                IDbundle.putString("catID", "catID");
                intentItemList.putExtras(IDbundle);
                startActivity(intentItemList);
            case R.id.catBtnPeople:
                IDbundle.putString("catName", "catName");
                IDbundle.putString("catID", "catID");
                intentItemList.putExtras(IDbundle);
                startActivity(intentItemList);
            case R.id.catBtnWorker:
                IDbundle.putString("catName", "catName");
                IDbundle.putString("catID", "catID");
                intentItemList.putExtras(IDbundle);
                startActivity(intentItemList);
            case R.id.catBtnCloth:
                IDbundle.putString("catName", "catName");
                IDbundle.putString("catID", "catID");
                intentItemList.putExtras(IDbundle);
                startActivity(intentItemList);
            case R.id.catBtnFood:
                IDbundle.putString("catName", "catName");
                IDbundle.putString("catID", "catID");
                intentItemList.putExtras(IDbundle);
                startActivity(intentItemList);
            case R.id.catBtnWeight:
                IDbundle.putString("catName", "catName");
                IDbundle.putString("catID", "catID");
                intentItemList.putExtras(IDbundle);
                startActivity(intentItemList);
            case R.id.catBtnAnimalDom:
                IDbundle.putString("catName", "catName");
                IDbundle.putString("catID", "catID");
                intentItemList.putExtras(IDbundle);
                startActivity(intentItemList);
            case R.id.catBtnAnimalWild:
                IDbundle.putString("catName", "catName");
                IDbundle.putString("catID", "catID");
                intentItemList.putExtras(IDbundle);
                startActivity(intentItemList);
            case R.id.catBtnPest:
                IDbundle.putString("catName", "catName");
                IDbundle.putString("catID", "catID");
                intentItemList.putExtras(IDbundle);
                startActivity(intentItemList);
            case R.id.catBtnBody:
                IDbundle.putString("catName", "catName");
                IDbundle.putString("catID", "catID");
                intentItemList.putExtras(IDbundle);
                startActivity(intentItemList);
            case R.id.catBtnDirection:
                IDbundle.putString("catName", "catName");
                IDbundle.putString("catID", "catID");
                intentItemList.putExtras(IDbundle);
                startActivity(intentItemList);
            case R.id.catBtnWord:
                IDbundle.putString("catName", "catName");
                IDbundle.putString("catID", "catID");
                intentItemList.putExtras(IDbundle);
                startActivity(intentItemList);
            case R.id.catBtnIllness:
                IDbundle.putString("catName", "catName");
                IDbundle.putString("catID", "catID");
                intentItemList.putExtras(IDbundle);
                startActivity(intentItemList);
            case R.id.catBtnSign:
                IDbundle.putString("catName", "catName");
                IDbundle.putString("catID", "catID");
                intentItemList.putExtras(IDbundle);
                startActivity(intentItemList);
            case R.id.catBtnConstruction:
                IDbundle.putString("catName", "catName");
                IDbundle.putString("catID", "catID");
                intentItemList.putExtras(IDbundle);
                startActivity(intentItemList);
            case R.id.catBtnStationaries:
                IDbundle.putString("catName", "catName");
                IDbundle.putString("catID", "catID");
                intentItemList.putExtras(IDbundle);
                startActivity(intentItemList);
            case R.id.catBtnSubject:
                IDbundle.putString("catName", "catName");
                IDbundle.putString("catID", "catID");
                intentItemList.putExtras(IDbundle);
                startActivity(intentItemList);
            case R.id.catBtnContinents:
                IDbundle.putString("catName", "catName");
                IDbundle.putString("catID", "catID");
                intentItemList.putExtras(IDbundle);
                startActivity(intentItemList);
            case R.id.catBtnCommonItems:
                IDbundle.putString("catName", "catName");
                IDbundle.putString("catID", "catID");
                intentItemList.putExtras(IDbundle);
                startActivity(intentItemList);
            case R.id.catBtnColor:
                IDbundle.putString("catName", "catName");
                IDbundle.putString("catID", "catID");
                intentItemList.putExtras(IDbundle);
                startActivity(intentItemList);
            case R.id.catBtnMath:
                IDbundle.putString("catName", "catName");
                IDbundle.putString("catID", "catID");
                intentItemList.putExtras(IDbundle);
                startActivity(intentItemList);
            case R.id.catBtnAboutUs:
                IDbundle.putString("catName", "catName");
                IDbundle.putString("catID", "catID");
                intentItemList.putExtras(IDbundle);
                startActivity(intentItemList);

            default:
                break;
        }

    }
}
