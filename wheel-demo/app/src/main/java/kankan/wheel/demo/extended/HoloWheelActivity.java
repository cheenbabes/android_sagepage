package kankan.wheel.demo.extended;

import kankan.wheel.widget.OnWheelChangedListener;
import kankan.wheel.widget.OnWheelScrollListener;
import kankan.wheel.widget.WheelView;
import kankan.wheel.widget.adapters.AbstractWheelTextAdapter;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class HoloWheelActivity extends Activity {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.cities_holo_layout);

        final WheelView books = (WheelView) findViewById(R.id.book);
        books.setVisibleItems(3); // Number of items
        books.setWheelBackground(R.drawable.wheel_bg_holo);
        books.setWheelForeground(R.drawable.wheel_val_holo);
        books.setShadowColor(0xFF000000, 0x88000000, 0x00000000);
        books.setViewAdapter(new BookAdapter(this));
        books.setCurrentItem(3);
        books.addChangingListener(changedListener);
        books.addScrollingListener(scrolledListener);

        final WheelView time = (WheelView) findViewById(R.id.time_length);
        time.setVisibleItems(3);
        time.setWheelBackground(R.drawable.wheel_bg_holo);
        time.setWheelForeground(R.drawable.wheel_val_holo);
        time.setShadowColor(0xFF000000, 0x88000000, 0x00000000);
        time.setViewAdapter(new TimeAdapter(this));
        time.setCurrentItem(2);
        time.addScrollingListener(scrolledListener);
        time.addChangingListener(changedListener);

        final WheelView digits = (WheelView) findViewById(R.id.digits);
        digits.setVisibleItems(3);
        digits.setWheelBackground(R.drawable.wheel_bg_holo);
        digits.setWheelForeground(R.drawable.wheel_val_holo);
        digits.setShadowColor(0xFF000000, 0x88000000, 0x00000000);
        //Built in numeric adapter but it has a different style
//        digits.setViewAdapter(new NumericWheelAdapter(this, 1, 30));
        digits.setViewAdapter(new NumberAdapter(this));
        digits.setCurrentItem(2);
        digits.addChangingListener(changedListener);
        digits.addScrollingListener(scrolledListener);

        //force update on initial app open
//        updateText();

    }

    private boolean wheelScrolled = false;

    OnWheelScrollListener scrolledListener = new OnWheelScrollListener() {
        @Override
        public void onScrollingStarted(WheelView wheelView) {
            wheelScrolled = true;
        }

        @Override
        public void onScrollingFinished(WheelView wheelView) {
            wheelScrolled = false;
            updateText();
        }
    };

    OnWheelChangedListener changedListener = new OnWheelChangedListener() {
        @Override
        public void onChanged(WheelView wheelView, int i, int i1) {
            if(!wheelScrolled){
                updateText();
            }
        }
    };

    private void updateText(){
        final WheelView books = (WheelView) findViewById(R.id.book);
        final WheelView time = (WheelView) findViewById(R.id.time_length);
        final WheelView digits = (WheelView) findViewById(R.id.digits);

        BookAdapter bookAdapter = new BookAdapter(this);
        TimeAdapter timeAdapter = new TimeAdapter(this);
        NumberAdapter numberAdapter = new NumberAdapter(this);

        String booksText = bookAdapter.getItemText(books.getCurrentItem()).toString();
        String timeText = timeAdapter.getItemText(time.getCurrentItem()).toString();
        String digitsText = numberAdapter.getItemText(digits.getCurrentItem()).toString();

        Integer numberSelected = Integer.parseInt(digitsText);
        if(numberSelected > 1){
            //lol hack for plurals
            digitsText = digitsText + "s";
        }

        int totalDays, pagesPerDay, bookPages, days = 0;
        switch(timeText){
            case "Week":
                days = 7;
                break;
            case "Month":
                days = 30;
                break;
            case "Year":
                days = 365;
                break;
        }

        switch(booksText){
            case "Sri Isopanishad":
                bookPages = 158;
                break;
            case "Krishna Book":
                bookPages = 706;
                break;
            case "Nectar of Instruction":
                bookPages = 97;
                break;
            case "Nectar of Devotion":
                bookPages = 407;
                break;
            case "Teachings of Lord Caitanya":
                bookPages = 347;
                break;
            case "Bhagavad Gita As It Is":
                bookPages = 868;
                break;
            case "Srimad Bhagavatam":
                bookPages = 15119;
                break;
            case "Caitanya Caritamrta":
                bookPages = 6621;
                break;
            default:
                bookPages =1;
        }

        totalDays = days * numberSelected;
        pagesPerDay = bookPages / totalDays;
        if(pagesPerDay <1){
            pagesPerDay = 1;
        }
        String pages= pagesPerDay > 1 ? "pages" : "page";

        TextView finalText = (TextView) findViewById(R.id.display_text);
        finalText.setText("You're trying to read " + booksText + " in " + digitsText +  " " + timeText +". You will need to read " + pagesPerDay + " " + pages + " a day");



    }

    private class NumberAdapter extends AbstractWheelTextAdapter {
        final String[] numbers = new String[]{"1", "2", "3", "4", "5", "6", "7", "8", "9", "10"};

        protected NumberAdapter(Context context) {
            super(context, R.layout.city_holo_layout, NO_RESOURCE);
            setItemTextResource(R.id.city_name);
        }

        @Override
        public View getItem(int index, View cachedView, ViewGroup parent) {
            View view = super.getItem(index, cachedView, parent);
            return view;
        }

        @Override
        public int getItemsCount() {
            return numbers.length;
        }

        @Override
        protected CharSequence getItemText(int index) {
            return numbers[index];
        }
    }

    private class TimeAdapter extends AbstractWheelTextAdapter {
        final String times[] = new String[]{"Week", "Month", "Year"};

        protected TimeAdapter(Context context) {
            super(context, R.layout.city_holo_layout, NO_RESOURCE);
            setItemTextResource(R.id.city_name);
        }

        @Override
        public View getItem(int index, View cachedView, ViewGroup parent) {
            View view = super.getItem(index, cachedView, parent);
            return view;
        }

        @Override
        public int getItemsCount() {
            return times.length;
        }

        @Override
        protected CharSequence getItemText(int index) {
            return times[index];
        }
    }

    /**
     * Adapter for countries
     */
    private class BookAdapter extends AbstractWheelTextAdapter {
        // City names
        final String cities[] = new String[]{"Sri Isopanishad", "Srimad Bhagavatam", "Krishna Book", "Caitanya Caritamrta", "Bhagavad Gita As It Is", "Teaching of Lord Caitanya", "Nectar of Instruction", "Nectar of Devotion"};

        /**
         * Constructor
         */
        protected BookAdapter(Context context) {
            super(context, R.layout.city_holo_layout, NO_RESOURCE);

            setItemTextResource(R.id.city_name);
        }

        @Override
        public View getItem(int index, View cachedView, ViewGroup parent) {
            View view = super.getItem(index, cachedView, parent);
            return view;
        }

        @Override
        public int getItemsCount() {
            return cities.length;
        }

        @Override
        protected CharSequence getItemText(int index) {
            return cities[index];
        }
    }
}

