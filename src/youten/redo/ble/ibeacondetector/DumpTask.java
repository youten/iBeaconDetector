/*
 * Copyright (C) 2014 youten
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package youten.redo.ble.ibeacondetector;

import java.lang.ref.WeakReference;
import java.util.List;

import youten.redo.ble.util.CsvDumpUtil;
import youten.redo.ble.util.ScannedDevice;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

public class DumpTask extends AsyncTask<List<ScannedDevice>, Integer, String> {
    private WeakReference<Context> mRef;

    public DumpTask(WeakReference<Context> ref) {
        mRef = ref;
    }

    @Override
    protected String doInBackground(List<ScannedDevice>... params) {
        if ((params == null) || (params[0] == null)) {
            return null;
        }

        List<ScannedDevice> list = params[0];
        String csvpath = CsvDumpUtil.dump(list);

        return csvpath;
    }

    @Override
    protected void onPostExecute(String result) {
        Context context = mRef.get();
        if (context != null) {
            if (result == null) {
                Toast.makeText(context, R.string.dump_failed, Toast.LENGTH_SHORT).show();
            } else {
                String suffix = context.getString(R.string.dump_succeeded_suffix);
                Toast.makeText(context, result + suffix, Toast.LENGTH_SHORT).show();
            }
        }
    }
}
