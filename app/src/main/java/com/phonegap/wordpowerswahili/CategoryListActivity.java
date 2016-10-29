package com.phonegap.wordpowerswahili;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.kobakei.ratethisapp.RateThisApp;

public class CategoryListActivity extends AppCompatActivity implements View.OnClickListener {

    Button catBtnGreetings, catBtnExprs, catBtnNumber, catBtnTime, catBtnDay, catBtnMonth, catBtnSeason, catBtnTransport, catBtnPeople, catBtnWorker, catBtnCloth, catBtnFood, catBtnWeight, catBtnAnimalDom, catBtnAnimalWild, catBtnPest, catBtnBody, catBtnDirection, catBtnWord, catBtnIllness, catBtnSign, catBtnConstruction, catBtnStationaries, catBtnSubject, catBtnContinets, catBtn, catBtnCommonItems, catBtnColor, catBtnMath, catBtnAboutUs;

    @Override
    public void onBackPressed() {
       // super.onBackPressed();

    }

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
    protected void onStart() {
        super.onStart();
      /*  RateThisApp.Config config = new RateThisApp.Config();
        config.setTitle(R.string.my_own_title);
        config.setMessage(R.string.my_own_message);
        config.setYesButtonText(R.string.my_own_rate);
        config.setNoButtonText(R.string.my_own_thanks);
        config.setCancelButtonText(R.string.my_own_cancel);*/

        // Custom criteria: 1 days and 1 launches
        RateThisApp.Config config = new RateThisApp.Config(3, 7);
        RateThisApp.init(config);
        // Monitor launch times and interval from installation
        RateThisApp.onStart(this);
        // If the criteria is satisfied, "Rate this app" dialog will be shown
        RateThisApp.showRateDialogIfNeeded(this);
    }

    @Override
    public void onClick(View v) {
        Bundle IDbundle = new Bundle();
        Intent intentItemList = new Intent(getApplicationContext(), ItemListActivity.class);

        switch (v.getId()) {
            case R.id.catBtnGreetings:
                IDbundle.putString("categoryID", "Greetings");
                intentItemList.putExtras(IDbundle);
                startActivity(intentItemList);
                break;
            case R.id.catBtnExprs:
                IDbundle.putString("categoryID", "Someusefulexpressions");
                intentItemList.putExtras(IDbundle);
                startActivity(intentItemList);
                break;

            case R.id.catBtnNumber:

                IDbundle.putString("categoryID", "Number");
                intentItemList.putExtras(IDbundle);
                startActivity(intentItemList);
                break;
            case R.id.catBtnTime:

                IDbundle.putString("categoryID", "Time");
                intentItemList.putExtras(IDbundle);
                startActivity(intentItemList);
                break;
            case R.id.catBtnDay:

                IDbundle.putString("categoryID", "DayOfTheWeek");
                intentItemList.putExtras(IDbundle);
                startActivity(intentItemList);
                break;
            case R.id.catBtnMonth:

                IDbundle.putString("categoryID", "Months");
                intentItemList.putExtras(IDbundle);
                startActivity(intentItemList);
                break;
            case R.id.catBtnSeason:

                IDbundle.putString("categoryID", "Seasons");
                intentItemList.putExtras(IDbundle);
                startActivity(intentItemList);
                break;
            case R.id.catBtnTransport:

                IDbundle.putString("categoryID", "Transportation");
                intentItemList.putExtras(IDbundle);
                startActivity(intentItemList);
                break;
            case R.id.catBtnPeople:

                IDbundle.putString("categoryID", "People");
                intentItemList.putExtras(IDbundle);
                startActivity(intentItemList);
                break;
            case R.id.catBtnWorker:

                IDbundle.putString("categoryID", "Workers");
                intentItemList.putExtras(IDbundle);
                startActivity(intentItemList);
                break;
            case R.id.catBtnCloth:

                IDbundle.putString("categoryID", "Clothing");
                intentItemList.putExtras(IDbundle);
                startActivity(intentItemList);
                break;
            case R.id.catBtnFood:

                IDbundle.putString("categoryID", "FoodstaffandFruits");
                intentItemList.putExtras(IDbundle);
                startActivity(intentItemList);
                break;
            case R.id.catBtnWeight:

                IDbundle.putString("categoryID", "WeightandMeasures");
                intentItemList.putExtras(IDbundle);
                startActivity(intentItemList);
                break;
            case R.id.catBtnAnimalDom:

                IDbundle.putString("categoryID", "DomesticAnimals");
                intentItemList.putExtras(IDbundle);
                startActivity(intentItemList);
                break;
            case R.id.catBtnAnimalWild:

                IDbundle.putString("categoryID", "WildAnimal");
                intentItemList.putExtras(IDbundle);
                startActivity(intentItemList);
                break;
            case R.id.catBtnPest:

                IDbundle.putString("categoryID", "Pests");
                intentItemList.putExtras(IDbundle);
                startActivity(intentItemList);
                break;
            case R.id.catBtnBody:

                IDbundle.putString("categoryID", "PartsoftheBody");
                intentItemList.putExtras(IDbundle);
                startActivity(intentItemList);
                break;
            case R.id.catBtnDirection:

                IDbundle.putString("categoryID", "Directions");
                intentItemList.putExtras(IDbundle);
                startActivity(intentItemList);
                break;
            case R.id.catBtnWord:

                IDbundle.putString("categoryID", "UsefulWord");
                intentItemList.putExtras(IDbundle);
                startActivity(intentItemList);
                break;
            case R.id.catBtnIllness:

                IDbundle.putString("categoryID", "Illness");
                intentItemList.putExtras(IDbundle);
                startActivity(intentItemList);
                break;
            case R.id.catBtnSign:

                IDbundle.putString("categoryID", "Signs");
                intentItemList.putExtras(IDbundle);
                startActivity(intentItemList);
                break;
            case R.id.catBtnConstruction:

                IDbundle.putString("categoryID", "Construction");
                intentItemList.putExtras(IDbundle);
                startActivity(intentItemList);
                break;
            case R.id.catBtnStationaries:

                IDbundle.putString("categoryID", "Stationers");
                intentItemList.putExtras(IDbundle);
                startActivity(intentItemList);
                break;
            case R.id.catBtnSubject:

                IDbundle.putString("categoryID", "Subjects");
                intentItemList.putExtras(IDbundle);
                startActivity(intentItemList);
                break;
            case R.id.catBtnContinents:

                IDbundle.putString("categoryID", "ContinentsandEnvironment");
                intentItemList.putExtras(IDbundle);
                startActivity(intentItemList);
                break;
            case R.id.catBtnCommonItems:

                IDbundle.putString("categoryID", "CommonItem");
                intentItemList.putExtras(IDbundle);
                startActivity(intentItemList);
                break;
            case R.id.catBtnColor:

                IDbundle.putString("categoryID", "Color");
                intentItemList.putExtras(IDbundle);
                startActivity(intentItemList);
                break;
            case R.id.catBtnMath:

                IDbundle.putString("categoryID", "MathematicalSigns");
                intentItemList.putExtras(IDbundle);
                startActivity(intentItemList);
                break;
            case R.id.catBtnAboutUs:

                IDbundle.putString("categoryID", "Greetings");
                intentItemList.putExtras(IDbundle);
                //  startActivity(intentItemList);                 break;

            default:
                break;
        }

    }
}
