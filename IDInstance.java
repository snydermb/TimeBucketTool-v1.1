package MRBS.VED;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

class IDInstance {
    private String mID;
    private Calendar mDate;
    private HashMap<String, TimeBucket> bucketMap;

    IDInstance(String id, String date) {
        mID = id;
        mDate = TimeBucketTool.stringToCal(date);
        bucketMap = new HashMap<>();
    }
    String getmID() {
        return mID;
    }
    void createGlobal(String name, Calendar globalA, Calendar globalB) {
        bucketMap.put(name, new TimeBucket(globalA, globalB));
    }
    void createBuckets(int daysPre, int daysPost, int interType, int[] pre, int[] post,
                       String[] preNames, String[] postNames, boolean[] allPre, boolean[] allPost) {
        Calendar beginDate = (Calendar)mDate.clone();
        Calendar endDate = (Calendar)mDate.clone();
        String name;
        if (allPre[0]) {
            name = "ALLPRE";
            if (allPre[1]) {
                endDate.add(Calendar.DATE, 1);
                name = name + "+Dx";
            }
            bucketMap.put(name, new TimeBucket(null, endDate));
        }
        if (allPost[0]) {
            name = "ALLPOST";
            if (allPost[1]) {
                beginDate.add(Calendar.DATE, -1);
                name = name + "+Dx";
            }
            bucketMap.put(name, new TimeBucket(beginDate, null));
        }
        beginDate = (Calendar)mDate.clone();
        endDate = (Calendar)mDate.clone();
        beginDate.add(Calendar.DATE, 0-daysPre);
        beginDate.add(Calendar.DATE, -1);
        endDate.add(Calendar.DATE, daysPost);
        endDate.add(Calendar.DATE, 1);
        bucketMap.put("Dx", new TimeBucket(beginDate, endDate));
        beginDate.add(Calendar.DATE, 1);
        for (int i = 0; i < pre.length; i++) {
            name = preNames[i];
            endDate = (Calendar)beginDate.clone();
            beginDate.add(interType, 0-pre[i]);
            beginDate.add(Calendar.DATE, -1);
            bucketMap.put(name, new TimeBucket(beginDate, endDate));
            beginDate.add(Calendar.DATE, 1);
        }
        endDate = (Calendar)mDate.clone();
        endDate.add(Calendar.DATE, daysPost);
        for (int i = 0; i < post.length; i++) {
            name = postNames[i];
            beginDate = (Calendar)endDate.clone();
            endDate.add(interType, post[i]);
            endDate.add(Calendar.DATE, 1);
            bucketMap.put(name, new TimeBucket(beginDate, endDate));
            endDate.add(Calendar.DATE, -1);
        }
    }
    void updateBuckets(String date, String vital) {
        for (TimeBucket tmp : bucketMap.values()) {
            if (tmp.checkValid(date)) {
                tmp.add(vital);
            }
        }
    }
    String[] returnOutline(ArrayList<String> bucketNames) {
        String[] outline = new String[(bucketNames.size()*4) + 1];
        outline[0] = this.mID;
        int pos = 1;
        for (int i = 0; i < bucketNames.size(); i++) {
            String bucketName = bucketNames.get(i);
            TimeBucket bucket = bucketMap.get(bucketName);
            int N = bucket.getN();
            outline[pos++] = N + "";
            if (N == 0) {
                outline[pos++] = "-";
                outline[pos++] = "-";
                outline[pos++] = "-";
            } else {
                outline[pos++] = String.format("%,.2f", bucket.getMin());
                outline[pos++] = String.format("%,.2f", bucket.getMax());
                outline[pos++] = String.format("%,.2f", bucket.getMean());
            }
        }
        return outline;
    }
}
