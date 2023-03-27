package com.mediatek.engineermode.mcfconfig;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import com.mediatek.engineermode.Elog;
import com.mediatek.engineermode.R;
import java.util.List;

public class FileInfoAdapter extends BaseAdapter {
    private static final float DEFAULT_ICON_ALPHA = 1.0f;
    private static final int FILE_TYPE_FILE = 0;
    private static final int FILE_TYPE_FOLDER = 1;
    private static final float HIDE_ICON_ALPHA = 0.3f;
    private static final String TAG = "McfConfig/FileInfoAdapter";
    private boolean mAllowMutilFile = false;
    /* access modifiers changed from: private */
    public List<FileInfo> mFileInfoList;
    private final LayoutInflater mInflater;
    private final Resources mResources;

    public FileInfoAdapter(Context context, List<FileInfo> fileList) {
        this.mResources = context.getResources();
        this.mInflater = LayoutInflater.from(context);
        this.mFileInfoList = fileList;
    }

    public void setAdapterList(List<FileInfo> fileList) {
        this.mFileInfoList = fileList;
    }

    public void allowMutilFile() {
        this.mAllowMutilFile = true;
    }

    public int getPosition(FileInfo fileInfo) {
        return this.mFileInfoList.indexOf(fileInfo);
    }

    public int getCount() {
        return this.mFileInfoList.size();
    }

    public FileInfo getItem(int pos) {
        return this.mFileInfoList.get(pos);
    }

    public long getItemId(int pos) {
        return (long) pos;
    }

    public View getView(int pos, View convertView, ViewGroup parent) {
        FileViewHolder viewHolder;
        View view = convertView;
        if (view == null) {
            view = this.mInflater.inflate(R.layout.mcf_adapter_fileinfos, (ViewGroup) null);
            viewHolder = new FileViewHolder((TextView) view.findViewById(R.id.edit_adapter_name), (TextView) view.findViewById(R.id.edit_adapter_size), (ImageView) view.findViewById(R.id.edit_adapter_img), (CheckBox) view.findViewById(R.id.file_select_cb));
            view.setTag(viewHolder);
            view.setTag(R.id.file_select_cb, viewHolder.mSelected);
        } else {
            viewHolder = (FileViewHolder) view.getTag();
        }
        viewHolder.mSelected.setTag(Integer.valueOf(pos));
        FileInfo currentItem = this.mFileInfoList.get(pos);
        viewHolder.mName.setText(currentItem.getFileName());
        view.setBackgroundColor(0);
        if (!currentItem.isDir()) {
            setSizeText(viewHolder.mSize, currentItem);
        }
        setIcon(viewHolder, currentItem);
        viewHolder.mSelected.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                int getPosition = ((Integer) buttonView.getTag()).intValue();
                ((FileInfo) FileInfoAdapter.this.mFileInfoList.get(getPosition)).setChecked(buttonView.isChecked());
                Elog.d("xing", "onCheckedChanged " + buttonView.isChecked() + " getPosition " + getPosition);
            }
        });
        if (!this.mAllowMutilFile) {
            viewHolder.mSelected.setVisibility(8);
        }
        return view;
    }

    private void setSizeText(TextView textView, FileInfo fileInfo) {
        textView.setText(this.mResources.getString(R.string.size) + " " + fileInfo.getFileSizeStr());
        textView.setVisibility(0);
    }

    private void setIcon(FileViewHolder viewHolder, FileInfo fileInfo) {
        viewHolder.mIcon.setImageBitmap(getIcon(fileInfo.isDir() ? 1 : 0));
        viewHolder.mIcon.setAlpha(DEFAULT_ICON_ALPHA);
        if (fileInfo.isHideFile()) {
            viewHolder.mIcon.setAlpha(HIDE_ICON_ALPHA);
        }
    }

    public Bitmap getIcon(int type) {
        int iconId;
        switch (type) {
            case 0:
                iconId = R.drawable.fm_file;
                break;
            case 1:
                iconId = R.drawable.fm_folder;
                break;
            default:
                iconId = R.drawable.fm_unknown;
                break;
        }
        return BitmapFactory.decodeResource(this.mResources, iconId);
    }

    static class FileViewHolder {
        protected ImageView mIcon;
        protected TextView mName;
        protected CheckBox mSelected;
        protected TextView mSize;

        public FileViewHolder(TextView name, TextView size, ImageView icon, CheckBox isSelected) {
            this.mName = name;
            this.mSize = size;
            this.mIcon = icon;
            this.mSelected = isSelected;
        }
    }
}
