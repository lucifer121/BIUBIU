package com.android.biubiu.fragment;

import cc.imeetu.iu.R;

import java.util.List;

import com.android.biubiu.MatchSettingActivity;
import com.android.biubiu.activity.LoginActivity;
import com.android.biubiu.activity.LoginOrRegisterActivity;
import com.android.biubiu.activity.RegisterOneActivity;
import com.android.biubiu.activity.RegisterThreeActivity;
import com.android.biubiu.chat.ChatActivity;
import com.android.biubiu.chat.DemoHelper;
import com.android.biubiu.chat.MyHintDialog;
import com.android.biubiu.chat.MyHintDialog.OnDialogClick;
import com.android.biubiu.chat.UserListActivity;
import com.android.biubiu.common.Constant;
import com.android.biubiu.utils.Constants;
import com.android.biubiu.utils.LogUtil;
import com.android.biubiu.utils.LoginUtils;
import com.android.biubiu.utils.SharePreferanceUtils;
import com.avos.avoscloud.LogUtil.log;
import com.hyphenate.EMMessageListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMConversation.EMConversationType;
import com.hyphenate.easeui.ui.EaseConversationListFragment;
import com.hyphenate.easeui.widget.EaseTitleBar;
import com.hyphenate.util.NetUtils;

import android.support.v4.app.Fragment;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemClickListener;


public class MenuRightFragment extends EaseConversationListFragment{
	private View mView;
	
	 private TextView errorText;
	 private Button register,login;
	 private String TAG="MenuRightFragment";
	 private ReceiveBroadCast receiveBroadCast;  //广播实例

	@Override
    protected void initView() {
        super.initView();
        View errorView = (LinearLayout) View.inflate(getActivity(),R.layout.right_menu, null);
        View noLoginView= (LinearLayout) View.inflate(getActivity(),R.layout.item_right_no_rigister, null);
        
        if(!LoginUtils.isLogin(getActivity())){
        	 errorItemContainer.addView(noLoginView);
        	 register=(Button) noLoginView.findViewById(R.id.register_item_btn);
        	 login=(Button) noLoginView.findViewById(R.id.login_item_btn);
        	 register.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					Intent intent=new Intent(getActivity(),RegisterThreeActivity.class);
					startActivity(intent);
				}
			});
        	 login.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					Intent intent=new Intent(getActivity(),LoginActivity.class);
					startActivity(intent);
				}
			});
        }else{
        	errorItemContainer.addView(errorView);
        	 errorText = (TextView) errorView.findViewById(R.id.tv_connect_errormsg); 
        }
        
        // 注册广播接收
        receiveBroadCast = new ReceiveBroadCast();
        IntentFilter filter = new IntentFilter();
        filter.addAction(Constants.FLAG_RECEIVE);    //只有持有相同的action的接受者才能接收此广播
        getActivity().registerReceiver(receiveBroadCast, filter);

    }
	public class ReceiveBroadCast extends BroadcastReceiver
	{
	 
	        @Override
	        public void onReceive(Context context, Intent intent)
	        {

	        	LogUtil.e(TAG, "收到刷新广播");
	        	handler.sendEmptyMessage(2);
	        	refresh();
	        }
	 
	}
    
    @Override
    protected void setUpView() {
    	 super.setUpView();
    	 titleBar.setTitle("biubiu消息");
    	 titleBar.setBackgroundColor(getResources().getColor(R.color.main_green)); 
    	 titleBar.setRightImageResource(R.drawable.mes_btn_people);
    	 titleBar.setRightLayoutClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			
			 if(!LoginUtils.isLogin(getActivity())){
		        	 Intent intent=new Intent(getActivity(),LoginOrRegisterActivity.class);
		        	 startActivity(intent);
		        	 }else{
		        		 startActivity(new Intent(getActivity(), UserListActivity.class));
		        	 }
			
		}
	});
//    	 if(DemoHelper.getInstance().isLoggedIn()==true){
//    		 log.e(TAG, "注册接收消息监听");
// 			EMClient.getInstance().chatManager().addMessageListener(msgListener);
//    	 }

        // 注册上下文菜单
        registerForContextMenu(conversationListView);
        conversationListView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                EMConversation conversation = conversationListView.getItem(position);
                String username = conversation.getUserName();
                if (username.equals(EMClient.getInstance().getCurrentUser()))
                    Toast.makeText(getActivity(), R.string.Cant_chat_with_yourself, 0).show();
                else {
                    // 进入聊天页面
                    Intent intent = new Intent(getActivity(), ChatActivity.class); 
                    intent.putExtra(Constant.EXTRA_USER_ID, username);
                    startActivity(intent);
                }
            }
        });
        conversationListView.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				// TODO Auto-generated method stub
	               EMConversation conversation = conversationListView.getItem(position);
	                final String username = conversation.getUserName();
				MyHintDialog.getDialog(getActivity(), "删除会话", "嗨~确定要删除会话吗", "确定", new OnDialogClick() {
					
					@Override
					public void onOK() {
						// TODO Auto-generated method stub
						       // 删除此会话
				        EMClient.getInstance().chatManager().deleteConversation(username, true);
				      
				        refresh();
						Toast.makeText(getActivity(), "删除成功", Toast.LENGTH_SHORT).show();
					}
					
					@Override
					public void onDismiss() {
						// TODO Auto-generated method stub
						
					}
				});
				return true;
			}
		});
       
    }

    @Override
    protected void onConnectionDisconnected() {
        super.onConnectionDisconnected();
    	
    
//        if (NetUtils.hasNetwork(getActivity())){
//         errorText.setText(R.string.can_not_connect_chat_server_connection);
//        } else {
//          errorText.setText(R.string.the_current_network);
//        }
    }
    
//	/**
//	 * 会话消息监听
//	 */
//	EMMessageListener msgListener = new EMMessageListener() {
//
//
//		@Override
//		public void onMessageReceived(List<EMMessage> messages) {
//			//收到消息
//			
//			log.e(TAG, "收到消息");
//			refresh();
//			handler.sendEmptyMessage(2);
//			
//		}
//
//		@Override
//		public void onCmdMessageReceived(List<EMMessage> messages) {
//			//收到透传消息
//			//收到消息
//			
//			log.e(TAG, "收到透传消息");
//			refresh();
//		}
//
//		@Override
//		public void onMessageReadAckReceived(List<EMMessage> messages) {
//			//收到已读回执
//		}
//
//		@Override
//		public void onMessageDeliveryAckReceived(List<EMMessage> message) {
//			//收到已送达回执
//		}
//
//		@Override
//		public void onMessageChanged(EMMessage message, Object change) {
//			//消息状态变动
//		}
//	};
    

}
