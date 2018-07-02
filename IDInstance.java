package MRBS.VED;

import java.util.ArrayList;
import java.util.Calendar;

class IDInstance {
    private String mID;
    private Calendar mDate;
    private ArrayList<TimeBucket> bucketList;

    IDInstance(String id, String date) {
        mID = id;
        mDate = TimeBucketTool.stringToCal(date);
        bucketList = new ArrayList<>();
    }
    String getmID() {
        return mID;
    }
    void createGlobal(Calendar globalA, Calendar globalB) {
        bucketList.add(new TimeBucket(globalA, globalB));
    }
    void createBuckets(int daysPre, int daysPost, int interType, int[] pre, int[] post,
                       String[] preNames, String[] postNames, boolean[] allPre, boolean[] allPost) {
        Calendar beginDate = (Calendar)mDate.clone();
        Calendar endDate = (Calendar)mDate.clone();
        if (allPre[0]) {
            if (allPre[1]) {
                endDate.add(Calendar.DATE, 1);
                bucketList.add(new TimeBucket(null, endDate));
            } else {
                bucketList.add(new TimeBucket(null, mDate));
            }
        }
        if (allPost[0]) {
            if (allPost[1]) {
                beginDate.add(Calendar.DATE, -1);
                bucketList.add(new TimeBucket(beginDate, null));
            } else {
                bucketList.add(new TimeBucket(mDate, null));
            }
        }
        beginDate = (Calendar)mDate.clone();
        endDate = (Calendar)mDate.clone();
        beginDate.add(Calendar.DATE, 0-daysPre);
        beginDate.add(Calendar.DATE, -1);
        endDate.add(Calendar.DATE, daysPost);
        endDate.add(Calendar.DATE, 1);
        bucketList.add(new TimeBucket(beginDate, endDate));
        beginDate.add(Calendar.DATE, 1);
        for (int i = 0; i < pre.length; i++) {
            endDate = (Calendar)beginDate.clone();
            beginDate.add(interType, 0-pre[i]);
            beginDate.add(Calendar.DATE, -1);
            bucketList.add(new TimeBucket(beginDate, endDate));
            beginDate.add(Calendar.DATE, 1);
        }
        endDate = (Calendar)mDate.clone();
        endDate.add(Calendar.DATE, daysPost);
        for (int i = 0; i < post.length; i++) {
            beginDate = (Calendar)endDate.clone();
            endDate.add(interType, post[i]);
            endDate.add(Calendar.DATE, 1);
            bucketList.add(new TimeBucket(beginDate, endDate));
            endDate.add(Calendar.DATE, -1);
        }
    }
    void updateBuckets(String date, String vital) {
        for (TimeBucket tmp : bucketList) {
            if (tmp.checkValid(date)) {
                tmp.add(vital);
            }
        }
    }
    void updateOutline(String[] outline) {
        outline[0] = this.mID;
        int pos = 1;
        for (int i = 0; i < bucketList.size(); i++) {
            int N = bucketList.get(i).getN();
            outline[pos++] = N + "";
            if (N == 0) {
                outline[pos++] = "-";
                outline[pos++] = "-";
                outline[pos++] = "-";
            } else {
                outline[pos++] = String.format("%,.2f", bucketList.get(i).getMin());
                outline[pos++] = String.format("%,.2f", bucketList.get(i).getMax());
                outline[pos++] = String.format("%,.2f", bucketList.get(i).getMean());
            }
        }
    }
}
