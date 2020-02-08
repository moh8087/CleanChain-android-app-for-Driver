package comemployee.cleanchain_sa.cleanchaindriver;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by moh on 24 أكت، 2017 م.
 */

public class DashboardRequest extends StringRequest {

    private static final String REGISTER_REQUEST_URL = "http://www.cleanchain-sa.com/cleanchain/dashboard_driver.php";
    private Map<String, String> params;

    public DashboardRequest(String did, Response.Listener<String> listener) {
        super(Method.POST, REGISTER_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("did", did);


    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
