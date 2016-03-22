package com.android.biubiu.chat;

import android.app.AlertDialog;
import android.content.Intent;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.biubiu.ContextMenuActivity;
import com.android.biubiu.MainActivity;
import com.android.biubiu.R;
import com.android.biubiu.activity.biu.MyPagerActivity;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMTextMessageBody;
import com.hyphenate.easeui.ui.EaseChatFragment;
import com.hyphenate.easeui.ui.EaseChatFragment.EaseChatFragmentListener;
import com.hyphenate.easeui.widget.chatrow.EaseCustomChatRowProvider;
import com.hyphenate.easeui.widget.emojicon.EaseEmojiconMenu;
import com.hyphenate.util.EasyUtils;

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
		// 设置标题栏点击事件
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
		intent.putExtra("username", username);
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

		// 消息框长按
		startActivityForResult((new Intent(getActivity(),
				ContextMenuActivity.class)).putExtra("message", message),
				REQUEST_CODE_CONTEXT_MENU);

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
				// Intent intent = new Intent(getActivity(),
				// ForwardMessageActivity.class);
				// intent.putExtra("forward_msg_id",
				// contextMenuMessage.getMsgId());
				// startActivity(intent);

				break;

			default:
				break;
			}
		}
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
				
                EMClient.getInstance().chatManager().deleteConversation(toChatUsername, true);
                messageList.refresh();
			}
		});
		deleteFriendLayout.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				portraidlg.dismiss();
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
			}
		});
	}

}
