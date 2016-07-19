//
//       _/_/_/                      _/            _/_/_/_/_/
//    _/          _/_/      _/_/    _/  _/              _/      _/_/      _/_/
//   _/  _/_/  _/_/_/_/  _/_/_/_/  _/_/              _/      _/    _/  _/    _/
//  _/    _/  _/        _/        _/  _/          _/        _/    _/  _/    _/
//   _/_/_/    _/_/_/    _/_/_/  _/    _/      _/_/_/_/_/    _/_/      _/_/
//
//
//  Copyright (c) 2015-2016, Geek Zoo Studio
//  http://www.geek-zoo.com
//
//
//  Permission is hereby granted, free of charge, to any person obtaining a
//  copy of this software and associated documentation files (the "Software"),
//  to deal in the Software without restriction, including without limitation
//  the rights to use, copy, modify, merge, publish, distribute, sublicense,
//  and/or sell copies of the Software, and to permit persons to whom the
//  Software is furnished to do so, subject to the following conditions:
//
//  The above copyright notice and this permission notice shall be included in
//  all copies or substantial portions of the Software.
//
//  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
//  IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
//  FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
//  AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
//  LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
//  FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS
//  IN THE SOFTWARE.
//

package com.dmd.zsb.protocol.request;


import com.orm.SugarRecord;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;


public class commentsendRequest  extends SugarRecord implements Serializable {

     public String   appkey;
     public String version;
     public String   sid;
     public String   uid;
     public String   cid;
     public String content;
     public int rank;

     public commentsendRequest() {
     }

     public commentsendRequest(String appkey, String version, String sid, String uid, String cid, String content, int rank) {
          this.appkey = appkey;
          this.version = version;
          this.sid = sid;
          this.uid = uid;
          this.cid = cid;
          this.content = content;
          this.rank = rank;
     }

     public void  fromJson(JSONObject jsonObject)  throws JSONException
     {
          if(null == jsonObject){
            return ;
           }
          JSONArray subItemArray;

          this.appkey = jsonObject.optString("appkey");
          this.version = jsonObject.optString("version");
          this.sid = jsonObject.optString("sid");
          this.uid = jsonObject.optString("uid");
          this.cid = jsonObject.optString("cid");
          this.content = jsonObject.optString("content");
          this.rank = jsonObject.optInt("rank");


          return ;
     }

     public JSONObject  toJson() throws JSONException 
     {
          JSONObject localItemObject = new JSONObject();
          JSONArray itemJSONArray = new JSONArray();

          localItemObject.put("appkey", appkey);
          localItemObject.put("version", version);
          localItemObject.put("uid", uid);
          localItemObject.put("sid", sid);
          localItemObject.put("cid", cid);
          localItemObject.put("content", content);
          localItemObject.put("rank", rank);
          return localItemObject;
     }

}
