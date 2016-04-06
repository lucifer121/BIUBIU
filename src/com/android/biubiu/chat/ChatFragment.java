package com.android.biubiu.chat;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.x;
import org.xutils.common.Callback.CancelledException;
import org.xutils.common.Callback.CommonCallback;
import org.xutils.http.RequestParams;

import android.app.AlertDialog;
import android.content.Intent;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import cc.imeetu.iu.R;

import com.android.biubiu.ContextMenuActivity;
import com.android.biubiu.MainActivity;
import com.android.biubiu.activity.biu.MyPagerActivity;
import com.android.biubiu.chat.MyHintDialog.OnDialogClick;
import com.android.biubiu.utils.HttpContants;
import com.android.biubiu.utils.LogUtil;
import com.android.biubiu.utils.SharePreferanceUtils;
import com.avos.avoscloud.LogUtil.log;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMTextMessageBody;
import com.hyphenate.easeui.ui.EaseChatFragment;
import com.hyphenate.easeui.ui.EaseChatFragment.EaseChatFragmentListener;
import com.hyphenate.easeui.widget.chatrow.EaseCustomChatRowProvider;
import com.hyphenate.easeui.widget.emojicon.EaseEmojiconMenu;
import com.hyphenate.util.EasyUtils;
import com.umeng.analytics.MobclickAgent;

public class ChatFragment extends EaseChatFragment implements
		EaseChatFragmentListener {
	private static final int REQUEST_CODE_CONTEXT_MENU = 14;

	@Override
	protected void initView() {
		// TODO Auto-generated method stub
		super.initView();
	}

	@Override
	protected void setUpView() {
		// TODO Auto-generated method stub
		super.setUpView();
		setChatFragmentListener(this);
		// 设置标题栏点击事件
		titleBar.setLeftImageResource(R.drawable.back_main);
		titleBar.setRightImageResource(R.drawable.mes_btn_right);
		
		titleBar.setRightLayoutClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
			//	Toast.makeText(getActivity(), "更多", Toast.LENGTH_SHORT).show();
				getMosterDialog();
			}
		});
		titleBar.setLeftLayoutClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (EasyUtils.isSingleActivity(getActivity())) {
					Intent intent = new Intent(getActivity(),
							MainActivity.class);
					startActivity(intent);
				}
				getActivity().setResult(getActivity().RESULT_CANCELED, getActivity().getIntent());
				getActivity().finish();
			}
		});
		titleBar.setBackgroundColor(getResources().getColor(R.color.main_green));
		// 添加了一组表情 卡耐基动图
		((EaseEmojiconMenu) inputMenu.getEmojiconMenu())
				.addEmojiconGroup(EmojiconExampleGroupData.getData());

	}

	@Override
	public void onSetMessageAttributes(EMMessage message) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onEnterToChatDetails() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onAvatarClick(String username) {
		// TODO Auto-generated method stub
		// 头像点击事件 进入他人主页
		Intent intent = new Intent(getActivity(), MyPagerActivity.class);
		intent.putExtra("userCode", username);
		startActivity(intent);
	}

	@Override
	public boolean onMessageBubbleClick(EMMessage message) {
		// TODO Auto-generated method stub
		// 消息框点击事件，demo这里不做覆盖，如需覆盖，return true
		return false;
	}

	@Override
	public void onMessageBubbleLongClick(EMMessage message) {
		// TODO Auto-generated method stub


		showDialog(message);

	}

	@Override
	public boolean onExtendMenuItemClick(int itemId, View view) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public EaseCustomChatRowProvider onSetCustomChatRowProvider() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == REQUEST_CODE_CONTEXT_MENU) {
			switch (resultCode) {
			case ContextMenuActivity.RESULT_CODE_COPY: // 复制消息
				clipboard.setText(((EMTextMessageBody) contextMenuMessage
						.getBody()).getMessage());
				break;
			case ContextMenuActivity.RESULT_CODE_DELETE: // 删除消息
				conversation.removeMessage(contextMenuMessage.getMsgId());
				messageList.refresh();
				break;

			case ContextMenuActivity.RESULT_CODE_FORWARD: // 转发消息
	
				break;

			default:
				break;
			}
		}
	}
	/**
	 * 长按消息框 复制  删除
	 */
	private void showDialog(final EMMessage message) {
		final AlertDialog portraidlg = new AlertDialog.Builder(getActivity()).create();
		portraidlg.show();
		Window win = portraidlg.getWindow();
		win.setContentView(R.layout.item_chatmessage_selector);
		TextView copy = (TextView) win
				.findViewById(R.id.copy_item_chatmessage_tv);
		copy.setOnClickListener(new OnClickListener() {

			@SuppressWarnings("deprecation")
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				log.e("复制");
				portraidlg.dismiss();
				clipboard.setText(((EMTextMessageBody) message
						.getBody()).getMessage());
			}
		});

		TextView delete = (TextView) win
				.findViewById(R.id.delete_item_chatmessage_tv);
		delete.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				log.e("删除");
				portraidlg.dismiss();
				conversation.removeMessage(message.getMsgId());
				messageList.refresh();
			}

		});
		// 点击item的上下区域 dralog消失
		View topView = win.findViewById(R.id.top_item_chatmessage_dialog_view);
		topView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				portraidlg.dismiss();

			}
		});
		View bottomView = win
				.findViewById(R.id.bottom_item_chatmessage_dialog_view);
		bottomView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				portraidlg.dismiss();
			}
		});

	}

	/**
	 * 更多
	 */
	public void getMosterDialog() {
		final AlertDialog portraidlg = new AlertDialog.Builder(getActivity())
				.create();
		portraidlg.show();
		Window win = portraidlg.getWindow();
		win.setContentView(R.layout.item_hint_moster_dralog_chat);
		
		RelativeLayout goHomeLayout,deleteMessageLayout,deleteFriendLayout,dismissLayout,jubaoLayout;
		goHomeLayout=(RelativeLayout) win.findViewById(R.id.go_userHome_dialog_rl);
		deleteMessageLayout=(RelativeLayout) win.findViewById(R.id.delete_messages_dialog_rl);
		deleteFriendLayout=(RelativeLayout) win.findViewById(R.id.delete_friend_dialog_rl);
		jubaoLayout=(RelativeLayout) win.findViewById(R.id.jubao_dialog_rl);
		dismissLayout=(RelativeLayout) win.findViewById(R.id.dismiss_dialog_rl);
		
		goHomeLayout.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				portraidlg.dismiss();
				Intent goHome=new Intent(getActivity(),MyPagerActivity.class);
				goHome.putExtra("userCode", toChatUsername);
				
				startActivity(goHome);
			}
		});
		deleteMessageLayout.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				portraidlg.dismiss();
				
				MyHintDialog.getDialog(getActivity(), "清空聊天记录", "嗨~确定要清空聊天记录吗？", "确定", new OnDialogClick() {
					
					@Override
					public void onOK() {
						// TODO Auto-generated method stub
			             EMClient.getInstance().chatManager().deleteConversation(toChatUsername, true);
			                messageList.refresh();
			                Toast.makeText(getActivity(), "清除成功", Toast.LENGTH_SHORT).show();;
					}
					
					@Override
					public void onDismiss() {
						// TODO Auto-generated method stub
						
					}
				});
   
			}
		});
		deleteFriendLayout.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				portraidlg.dismiss();
				
				MyHintDialog.getDialog(getActivity(), "解除关系", "嗨~确定解除和Ta的关系吗？", "确定", new OnDialogClick() {
					
					@Override
					public void onOK() {
						// TODO Auto-generated method stub
					removeFriend(toChatUsername);
					//	getActivity().finish();
					}
					
					@Override
					public void onDismiss() {
						// TODO Auto-generated method stub
						
					}
				});
				
			}
		});
		dismissLayout.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				portraidlg.dismiss();
			}
		});
		jubaoLayout.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				portraidlg.dismiss();
				MyHintDialog.getDialog(getActivity(), "举报Ta", "嗨~确定要举报Ta吗？", "确定", new OnDialogClick() {
					
					@Override
					public void onOK() {
						// TODO Auto-generated method stub  缺少举报接口

//			                Toast.makeText(getActivity(), "举报成功", Toast.LENGTH_SHORT).show();;
			                jubao(toChatUsername);
					}
					
					@Override
					public void onDismiss() {
						// TODO Auto-generated method stub
						
					}
				});
			}
		});
	}
	
	/**
	 * 删除好友
	 */
		private void removeFriend(String userCode){
			RequestParams params=new RequestParams(HttpContants.HTTP_ADDRESS+HttpContants.REMOVE_FRIEND);
			JSONObject object=new JSONObject();
			try {
				object.put("token", SharePreferanceUtils.getInstance().
						getToken(getActivity(), SharePreferanceUtils.TOKEN, ""));
				object.put("device_code", SharePreferanceUtils.getInstance().
						getDeviceId(getActivity(), SharePreferanceUtils.DEVICE_ID, ""));
				object.put("user_code", userCode);
				
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			params.addBodyParameter("data", object.toString());
			x.http().post(params, new CommonCallback<String>() {

				@Override
				public void onCancelled(CancelledException arg0) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void onError(Throwable arg0, boolean arg1) {
					// TODO Auto-generated method stub
					
					Toast.makeText(getActivity(), "解除关系失败", Toast.LENGTH_SHORT).show();
					LogUtil.e(TAG, "解除匹配失败");
				}

				@Override
				public void onFinished() {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void onSuccess(String arg0) {
					// TODO Auto-generated method stub
					LogUtil.e(TAG, arg0.toString());
					JSONObject jsonObject;
					try {
						jsonObject=new JSONObject(arg0);
						String state=jsonObject.getString("state");
						LogUtil.e(TAG, state);
						if(!state.equals("200")){
								
							Toast.makeText(getActivity(), jsonObject.getString("error"), Toast.LENGTH_SHORT).show();
						}
						
						Toast.makeText(getActivity(), "解除关系成功", Toast.LENGTH_SHORT).show();
						LogUtil.e(TAG, "解除匹配成功");
						JSONObject jsonObject2=new JSONObject();
						jsonObject2=jsonObject.getJSONObject("data");
//						String token=jsonObject2.getString("token");
//						if(!token.equals("")&&token!=null){
//							SharePreferanceUtils.getInstance().putShared(getActivity(), SharePreferanceUtils.TOKEN, token);
//						}						
					Intent intent=getActivity().getIntent();
					getActivity().setResult(getActivity().RESULT_OK, intent);
					getActivity().finish();	
									
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}
			});
			
		}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		Intent intent=getActivity().getIntent();
		//100表示解除成功
		getActivity().setResult(getActivity().RESULT_CANCELED, intent);
		getActivity().finish();	
	}
	/**
	 * 举报好友
	 * @param userCode
	 */
	public void jubao(String userCode){
		RequestParams params=new RequestParams(HttpContants.HTTP_ADDRESS+HttpContants.REPORT);
		JSONObject object=new JSONObject();
		try {
			object.put("token", SharePreferanceUtils.getInstance().
					getToken(getActivity(), SharePreferanceUtils.TOKEN, ""));
			object.put("device_code", SharePreferanceUtils.getInstance().
					getDeviceId(getActivity(), SharePreferanceUtils.DEVICE_ID, ""));
			object.put("user_code", userCode);
			object.put("reason", "聊天页面举报");
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		params.addBodyParameter("data", object.toString());
		x.http().post(params, new CommonCallback<String>() {

			@Override
			public void onCancelled(CancelledException arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onError(Throwable arg0, boolean arg1) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onFinished() {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onSuccess(String arg0) {
				// TODO Auto-generated method stub
				LogUtil.e(TAG, arg0);
				
				JSONObject jsonObject;
				try {
					jsonObject=new JSONObject(arg0);
					String state=jsonObject.getString("state");
					LogUtil.e(TAG, state);
					if(!state.equals("200")){
							
						Toast.makeText(getActivity(), jsonObject.getString("error"), Toast.LENGTH_SHORT).show();
					}
					Toast.makeText(getActivity().getApplicationContext(), "举报成功", Toast.LENGTH_SHORT).show();
					
					JSONObject jsonObject2=new JSONObject();
					jsonObject2=jsonObject.getJSONObject("data");
//					String token=jsonObject2.getString("token");
//					if(!token.equals("")&&token!=null){
//						SharePreferanceUtils.getInstance().putShared(getActivity(), SharePreferanceUtils.TOKEN, token);
//					}						
				
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		});
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		MobclickAgent.onResume(getActivity());
	}

	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		MobclickAgent.onPause(getActivity());
	}
		

}
