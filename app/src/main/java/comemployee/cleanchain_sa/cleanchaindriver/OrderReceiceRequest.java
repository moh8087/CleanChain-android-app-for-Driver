package comemployee.cleanchain_sa.cleanchaindriver;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by moh on 24 أكت، 2017 م.
 */

public class OrderReceiceRequest extends StringRequest {

    private static final String REGISTER_REQUEST_URL = "http://www.cleanchain-sa.com/cleanchain/receiveorder_driver.php";
    private Map<String, String> params;

    public OrderReceiceRequest(String id, Response.Listener<String> listener) {
        super(Request.Method.POST, REGISTER_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("id", id);


    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}

