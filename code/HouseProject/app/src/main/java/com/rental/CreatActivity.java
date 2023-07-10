package com.rental;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import net.tsz.afinal.http.AjaxParams;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.pony.adapter.PractitionersAdapter;
import com.pony.adapter.SelectedImageAdapter;
import com.pony.base.BaseActivity;
import com.pony.config.Consts;
import com.pony.db.MemberUserUtils;
import com.pony.model.CategoryModel;
import com.pony.model.HouseModel;
import com.pony.model.ResponseEntry;
import com.pony.model.SelectImageItem;
import com.pony.observable.HouseObservable;
import com.pony.photo.ui.SelectImagesActivity;
import com.pony.photo.ui.ShowCreatePicturesActivity;
import com.pony.util.LoadingDialog;
import com.pony.util.ToastUtil;
import com.pony.util.UploadUtils;
import com.pony.view.DialogListMsg;
import com.pony.view.GridLayout;
import com.pony.view.ImageItemClickListner;

public class CreatActivity extends BaseActivity implements ImageItemClickListner {
	// 标题
	private TextView mTvTitle;
	// 返回
	private ImageView mIvBack;
	GridLayout grid_instructor;
	HorizontalScrollView horizontalscrollview;
	private ArrayList<SelectImageItem> imageItems = new ArrayList<SelectImageItem>();
	private SelectedImageAdapter selectedImageAdapter;
	private Button mSubmit;
	private File imgPath;
	public LoadingDialog mdialog;
	private List<String> mListImage = new ArrayList<String>();
	private int imgPosFlag = 0;
	private String picPath = null;
	private EditText post_title;
	private EditText post_content;
	// 图片上传表示位
	private int imageFlagNumber = 0;
	private int ctposNumber = 0;
	
	private EditText metMoney;
	private EditText metName;
	private EditText metMessage;
	private EditText metPhone;
	private List<CategoryModel> list_result = new ArrayList<CategoryModel>();
	private DialogListMsg dialogListMsg;
	private String choiceType = "租赁";
	
	private EditText houseAddress;
	private EditText housePeiTao;
	private RadioGroup mrdChoice;
	private RadioButton mrb1 = null;
	private RadioButton mrb2 = null;
	
	private Button mbtnCt;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_creat);
		
		
		initWidget();
		initData();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.mIvBack:
			finish();
			break;
		case R.id.mbtnCt:
			dialogListMsg.Show();
			break;
		case R.id.mSubmit:

			Log.e("pony_log", imageFlagNumber + "");

			mdialog.show();
			UploadFileTask uploadFileTask = new UploadFileTask(this);
			uploadFileTask.execute(mListImage.get(imageFlagNumber));

			break;

		}
	}

	@Override
	public void initWidget() {


		mrdChoice = (RadioGroup) findViewById(R.id.mrgChoice);
		mrb1 = (RadioButton) findViewById(R.id.mrb1);
		mrb2 = (RadioButton) findViewById(R.id.mrb2);


		houseAddress = (EditText) findViewById(R.id.houseAddress);
		housePeiTao = (EditText) findViewById(R.id.housePeiTao);
		dialogListMsg = new DialogListMsg(this);
		dialogListMsg.setTitle().setText("请选择房源类型");
		metPhone = (EditText) findViewById(R.id.metPhone);
		metMessage = (EditText) findViewById(R.id.metMessage);
		metMoney = (EditText) findViewById(R.id.metMoney);
		metName = (EditText) findViewById(R.id.metName);
		mbtnCt = (Button) findViewById(R.id.mbtnCt);
		mdialog = new LoadingDialog(this, "上传图片...");
		mSubmit = (Button) findViewById(R.id.mSubmit);
		grid_instructor = (GridLayout) findViewById(R.id.grid_instructor);
		horizontalscrollview = (HorizontalScrollView) findViewById(R.id.horizontalscrollview);
		mIvBack = (ImageView) findViewById(R.id.mIvBack);
		mTvTitle = (TextView) findViewById(R.id.mTvTitle);
		mTvTitle.setText("发布房源");
		mIvBack.setVisibility(View.VISIBLE);
		mIvBack.setOnClickListener(this);
		mSubmit.setOnClickListener(this);
		mbtnCt.setOnClickListener(this);
	}

	@Override
	public void initData() {
		mrdChoice.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				if (checkedId == mrb1.getId()) {
					choiceType = "租赁";
				} else if (checkedId == mrb2.getId()) {
					choiceType = "卖房";
				}
			}
		});

		metName.setText("玥下溪水小区");
		metMoney.setText("1000");
		metPhone.setText("15249246003");
		houseAddress.setText("西安市高新区");
		housePeiTao.setText("电视，沙发，空调；洗衣机；衣柜");
		metMessage.setText("50");

		MessageAction(true);
		ImageLoader.getInstance().init(ImageLoaderConfiguration.createDefault(this));
		initSelectedGridView();
		
		dialogListMsg.show_listview().setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
				ctposNumber = position;
				mbtnCt.setText(list_result.get(position).getTypeName());
				dialogListMsg.Close();

			}
		});

		dialogListMsg.submit_no().setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				dialogListMsg.Close();
			}
		});
	}

	private void initSelectedGridView() {
		selectedImageAdapter = new SelectedImageAdapter(this, imageItems);
		SelectImageItem addItem = new SelectImageItem();
		addItem.setSid(100);// 添加的图标
		imageItems.add(addItem);
		selectedImageAdapter.notifyDataSetChanged();
		grid_instructor.setGridAdapter(selectedImageAdapter, CreatActivity.this);

	}

	@Override
	public void imageItemClick(int position, SelectImageItem imageItem) {
		if (imageItem != null) {
			int sid = imageItem.getSid();
			if (sid == 100) {

				if (CreatActivity.this.getIntent().getIntExtra("photo_message", 0) == 1) {
					goCameraActivity();
				} else {
					// 添加图片
					Intent intentImages = new Intent(CreatActivity.this, SelectImagesActivity.class);
					intentImages.putExtra("image_count", imageItems.size());
					intentImages.putExtra("max_count", "9");
					startActivityForResult(intentImages, 1);
				}
			} else {
				Intent intentPicture = new Intent(CreatActivity.this, ShowCreatePicturesActivity.class);
				intentPicture.putExtra("position", position);
				intentPicture.putExtra("piction_path", imageItems);
				startActivityForResult(intentPicture, 3);
			}
		}
	}

	private void addNewItemWithPre(String cameraPath) {

		int count = selectedImageAdapter.getCount();
		if (count > 0) {
			int selectCount = count - 1;
			SelectImageItem item = new SelectImageItem();
			item.setUrl(cameraPath);
			imageItems.add(selectCount, item);
		}
	}

	private void scrollgridView() {
		final int count = selectedImageAdapter.getCount();
		if (count > 1) {
			new Handler().postDelayed(new Runnable() {
				@Override
				public void run() {
					horizontalscrollview.smoothScrollTo(2000, 0);
				}
			}, 500);
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if (requestCode == 5) {
			Log.d("camera", "" + (data == null));
			if (mCameraFile == null || !mCameraFile.exists()) {
				return;
			}
			imgPath = mCameraFile;
			mListImage.add(mCameraFile.getAbsolutePath());
			addNewItemWithPre(mCameraFile.getAbsolutePath());
		}

		if (requestCode == 1) {
			if (data != null) {

				if (0 == data.getIntExtra("action", -1)) {
					String cameraPath = data.getStringExtra("camera_path");
					addNewItemWithPre(cameraPath);

				} else if (1 == data.getIntExtra("action", -1)) {
					ArrayList<CharSequence> charSequences = data.getCharSequenceArrayListExtra("images");
					for (CharSequence ss : charSequences) {
						Log.e("pony_log", "image:" + ss.toString());
						picPath = ss.toString();
						mListImage.add(ss.toString());
						addNewItemWithPre(ss.toString());
					}
				}
				selectedImageAdapter.notifyDataSetChanged();
				scrollgridView();
			}
		}

		if (requestCode == 3) {
			if (data != null) {
				@SuppressWarnings("unchecked")
				ArrayList<SelectImageItem> imgUrls = (ArrayList<SelectImageItem>) data.getSerializableExtra("data");
				if (imgUrls != null && imgUrls.size() > 0) {
					imageItems.clear();
					imageItems.addAll(imgUrls);
					selectedImageAdapter.notifyDataSetChanged();
					scrollgridView();
				}
			}
		}
		grid_instructor.setGridAdapter(selectedImageAdapter, CreatActivity.this); 
	}

	private Uri mOutPutFileUri;
	private File mCameraFile;

	// 去拍照
	private void goCameraActivity() {
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		// dailyyoga_camera文件夹
		File parentFile = new File(Environment.getExternalStorageDirectory().toString() + "/dailyyoga_camera");
		if (!parentFile.exists()) {
			parentFile.mkdirs();
		}
		mCameraFile = new File(parentFile + "/" + System.currentTimeMillis() + ".jpg");
		mOutPutFileUri = Uri.fromFile(mCameraFile);
		intent.putExtra(MediaStore.EXTRA_OUTPUT, mOutPutFileUri);
		startActivityForResult(intent, 5);
	}

	String createTome;
	private void createTopicPost(boolean isShow) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");// 设置日期格式
		createTome = df.format(new Date());
		//对图片路径的处理
		String imagePath = "";
		for (int i = 0; i < mListImage.size(); i++) {
			String[] arrPath = mListImage.get(i).split("\\/");
			imagePath = arrPath[arrPath.length - 1] +","+imagePath;
		}

		AjaxParams params = new AjaxParams();
		params.put("action_flag", "addMessage");
		params.put("houseName", metName.getText().toString());
		params.put("houseMoney", metMoney.getText().toString());
		params.put("housePhone",metPhone.getText().toString());
		params.put("houseMessage",metMessage.getText().toString());
		params.put("userId",MemberUserUtils.getUid(this));
		params.put("userName",MemberUserUtils.getName(this));
		params.put("image", imagePath.substring(0, imagePath.length()-1));
		params.put("houseCategory",list_result.get(ctposNumber).getTypeId()+"");
		params.put("houseCategoryName",list_result.get(ctposNumber).getTypeName()+"");

		params.put("houseCreatime",createTome);
		
		params.put("houseAddress",houseAddress.getText().toString());
		params.put("housePeiTao",housePeiTao.getText().toString());
		params.put("houseType",choiceType);

		httpPost(Consts.URL + Consts.APP.HouseAction, params, Consts.actionId.resultCode, isShow, "正在上传...");

	}

	@Override
	protected void callBackSuccess(ResponseEntry entry, int actionId) {
		super.callBackSuccess(entry, actionId);
		switch (actionId) {
		case Consts.actionId.resultFlag:
			if (null != entry.getData() && !TextUtils.isEmpty(entry.getData())) {

				String jsonMsg = entry.getData().substring(1, entry.getData().length() - 1);
				if (null != jsonMsg && !TextUtils.isEmpty(jsonMsg)) {
					list_result = mGson.fromJson(entry.getData(), new TypeToken<List<CategoryModel>>() {
					}.getType());
					PractitionersAdapter practitionersAdapter = new PractitionersAdapter(CreatActivity.this);
					practitionersAdapter.setData(list_result);
					dialogListMsg.show_listview().setAdapter(practitionersAdapter);
				}
			}
			break;
		case Consts.actionId.resultCode:

			mdialog.dismiss();
			ToastUtil.show(CreatActivity.this, "发布成功");
			
			String imagePath = "";
			for (int i = 0; i < mListImage.size(); i++) {
				String[] arrPath = mListImage.get(i).split("\\/");
				imagePath = arrPath[arrPath.length - 1] + "," + imagePath;
			}
			
//			params.put("houseName", metName.getText().toString());
//			params.put("houseMoney", metMoney.getText().toString());
//			params.put("housePhone",metPhone.getText().toString());
//			params.put("houseMessage",metMessage.getText().toString());
//			params.put("userId",MemberUserUtils.getUid(this));
//			params.put("image", imagePath.substring(0, imagePath.length()-1));
//			params.put("houseCategory",list_result.get(ctposNumber).getTypeId()+"");
			
			
			HouseModel  houseModel = new HouseModel();
			houseModel.setHouseMoney(metMoney.getText().toString());
			houseModel.setHouseName(metName.getText().toString());
			houseModel.setHousePhone(metPhone.getText().toString());
			houseModel.setHouseMessage(metMessage.getText().toString());
			houseModel.setUserId(MemberUserUtils.getUid(this));
			houseModel.setHouseImage(imagePath.substring(0, imagePath.length() - 1));
			houseModel.setHouseCategory(list_result.get(ctposNumber).getTypeId()+"");
			houseModel.setHouseCreatime(createTome);
			houseModel.setHouseId( entry.getRepMsg());
			houseModel.setTypeName(mbtnCt.getText().toString());
			HouseObservable.getInstance().notifyStepChange(houseModel);
			
			
			new Handler().postDelayed(new Runnable() {
				@Override
				public void run() {
					finish();
				}
			}, 2000);
			break;
		}
	}

	@Override
	protected void callBackAllFailure(String strMsg, int actionId) {
		super.callBackAllFailure(strMsg, actionId);
		mdialog.dismiss();
		ToastUtil.show(CreatActivity.this, strMsg);

	}

	public class UploadFileTask extends AsyncTask<String, Void, String> {
		/**
		 * 可变长的输入参数，与AsyncTask.exucute()对应
		 */
		private Activity context = null;

		public UploadFileTask(Activity ctx) {
			this.context = ctx;

		}

		@Override
		protected void onPostExecute(String result) {
			imageFlagNumber++;
			if (UploadUtils.SUCCESS.equalsIgnoreCase(result)) {
				if (imageFlagNumber < mListImage.size()) {
					UploadFileTask uploadFileTask = new UploadFileTask(CreatActivity.this);
					uploadFileTask.execute(mListImage.get(imageFlagNumber));
				} else if (imageFlagNumber == mListImage.size()) {
					// 返回HTML页面的内容
					createTopicPost(true);
				}
			} else {
				ToastUtil.show(CreatActivity.this, " 图片上传失败");
			}
		}

		@Override
		protected void onPreExecute() {
		}

		@Override
		protected void onCancelled() {
			super.onCancelled();
		}

		@Override
		protected String doInBackground(String... params) {
			File file = new File(params[0]);
			return UploadUtils.uploadFile(file, Consts.URL + Consts.APP.HouseAction+"?action_flag=addMessage");
		}

		@Override
		protected void onProgressUpdate(Void... values) {
		}

	}
	

	private void MessageAction(boolean isShow) {
		AjaxParams params = new AjaxParams();
		params.put("action_flag", "listTypePhoneMessage");
		httpPost(Consts.URL + Consts.APP.ShopAction, params, Consts.actionId.resultFlag, isShow, "正在加载...");
	}
	
}
