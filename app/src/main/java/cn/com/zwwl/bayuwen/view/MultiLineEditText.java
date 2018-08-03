package cn.com.zwwl.bayuwen.view;


import android.content.Context;
import android.support.v7.widget.AppCompatEditText;
import android.util.AttributeSet;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;

/**
 * lmj  on 2018/6/29
 */
public class MultiLineEditText extends AppCompatEditText{
    public MultiLineEditText(Context context) {
        super(context);
    }

    public MultiLineEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MultiLineEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    public InputConnection onCreateInputConnection(EditorInfo outAttrs) {

            InputConnection connection = super.onCreateInputConnection(outAttrs);
            int imeActions = outAttrs.imeOptions ;
            if ((outAttrs.imeOptions & EditorInfo.IME_ACTION_SEND) != 0) {
                // clear the existing action
                outAttrs.imeOptions ^= imeActions;
                // set the DONE action
                outAttrs.imeOptions |= EditorInfo.IME_ACTION_SEND;
            }
            if ((outAttrs.imeOptions& EditorInfo.IME_FLAG_NO_ENTER_ACTION) != 0) {
                outAttrs.imeOptions &= EditorInfo.IME_FLAG_NO_ENTER_ACTION;
            }
            return connection;
        }


}
