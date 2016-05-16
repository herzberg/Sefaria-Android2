package org.sefaria.sefaria.TextElements;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.LinearLayout;

import org.sefaria.sefaria.R;
import org.sefaria.sefaria.Settings;
import org.sefaria.sefaria.Util;
import org.sefaria.sefaria.database.API;
import org.sefaria.sefaria.database.Book;
import org.sefaria.sefaria.database.Text;
import org.sefaria.sefaria.layouts.SefariaTextView;

import java.util.Arrays;
import java.util.Collections;
import java.util.regex.Pattern;

/**
 * Created by nss on 11/4/15.
 */
public class TextChapterHeader extends LinearLayout {

    public static final String[] ANONYMOUS_HEADER_CATS = {"Tanach","Mishnah","Talmud","Tosefta"}; //list of categories whose headers shouldn't be shown (b/c they're too obvious)

    //private Util.Lang lang;
    private SefariaTextView tv;
    public String enText;
    public String heText;
    //public Pattern digitPat;

    public TextChapterHeader(Context context, Text segment, float textSize) {
        super(context);
        enText = segment.getText(Util.Lang.EN);
        heText = segment.getText(Util.Lang.HE);
        init(context,textSize);
    }

    public TextChapterHeader(Context context, AttributeSet attrSet) {
        super(context, attrSet);
        enText = "Section";
        heText = "Section";
        init(context, 0f);
    }

    public TextChapterHeader(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
        enText = "Section";
        heText = "Section";
        init(context, 0f);
    }

    private void init(Context context, float textSize) {
        inflate(context, R.layout.text_chapter_header, this);
        tv = (SefariaTextView) findViewById(R.id.tv);

        Util.Lang lang = Settings.getMenuLang();
        setLang(lang);
        setTextSize(textSize);

        //digitPat = Pattern.compile("\\d{1,}$");
    }

    public void setLang(Util.Lang lang) {
        tv.setFont(lang, true);
        if (lang == Util.Lang.HE) {
            tv.setText(heText);
        }
        else {
            tv.setText(enText);
        }
    }

    public void setSectionTitle(Text segment, Book book) {
        String cat = book.getRootCategory();
        boolean isAnonymous = Arrays.asList(ANONYMOUS_HEADER_CATS).indexOf(cat) != -1;
        enText = segment.getText(Util.Lang.EN);
        heText = segment.getText(Util.Lang.HE);
        if (isAnonymous) {
            String[] enTextSplit = enText.split(" ");
            String[] heTextSplit = heText.split(" ");
            enText = enTextSplit[enTextSplit.length-1];
            heText = heTextSplit[heTextSplit.length-1];
        }

        setLang(Settings.getMenuLang());
    }

    public void setTextSize(float textSize) {
        tv.setTextSize(textSize);
    }

    public float getTextSize() { return tv.getTextSize(); }
}
