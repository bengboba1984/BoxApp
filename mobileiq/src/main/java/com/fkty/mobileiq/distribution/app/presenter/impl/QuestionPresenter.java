package com.fkty.mobileiq.distribution.app.presenter.impl;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.fkty.mobileiq.distribution.app.model.IQuestionModel;
import com.fkty.mobileiq.distribution.app.model.impl.QuestionModel;
import com.fkty.mobileiq.distribution.app.presenter.IQuestionPresenter;
import com.fkty.mobileiq.distribution.app.view.IQuestionView;
import com.fkty.mobileiq.distribution.basic.BasicPresenter;
import com.fkty.mobileiq.distribution.bean.TestParamsBean;
import com.fkty.mobileiq.distribution.bean.TestTypeBean;
import com.fkty.mobileiq.distribution.constant.CommonField;
import com.fkty.mobileiq.distribution.constant.QuestionConstant;
import com.fkty.mobileiq.distribution.constant.ServerErrorCode;
import com.fkty.mobileiq.distribution.inter.IBasicHandler;

import java.util.List;

/**
 * Created by frank_tracy on 2018/3/19.
 */

public class QuestionPresenter extends BasicPresenter<IQuestionView, IQuestionModel>
        implements IQuestionPresenter, QuestionConstant
{
    private static QuestionPresenter presenter;
    private QuestionModel mBasicModel;
    private IQuestionView mBasicView;
    private IBasicHandler.Callback testCallback = new IBasicHandler.Callback()
    {
        public void call(int paramInt, Bundle paramBundle)
        {
            switch (paramInt)
            {
                default:
                    break;
                case CommonField.MODLE_STATUS_READY:
                    Handler h = QuestionPresenter.this.getHandler();
                    h.sendEmptyMessage(CommonField.MODLE_STATUS_READY);
                    break;
                case CommonField.MODLE_STATUS_TESTING:
                    Message.obtain(QuestionPresenter.this.getHandler(), CommonField.MODLE_STATUS_TESTING, paramBundle).sendToTarget();
                    break;
                case CommonField.MODLE_STATUS_FINISH:
                    Message.obtain(QuestionPresenter.this.getHandler(), CommonField.MODLE_STATUS_FINISH, paramBundle).sendToTarget();
                    break;
                case CommonField.MODLE_STATUS_STOP:
                    Message.obtain(QuestionPresenter.this.getHandler(), CommonField.MODLE_STATUS_STOP, paramBundle).sendToTarget();
                    break;
                case ServerErrorCode.ERROR_CODE_FAILED:
                    Message.obtain(QuestionPresenter.this.getHandler(), ServerErrorCode.ERROR_CODE_FAILED, paramBundle).sendToTarget();
                    break;
            }

        }

        public void onError(int paramInt, Bundle paramBundle)
        {
        }

        public void onFailed(int paramInt, Bundle paramBundle)
        {
            switch (paramInt)
            {
                default:
                    break;
                case TEST_STOP_SUCCESS:
                    break;
                case TEST_START_FAILED:
                    Message.obtain(QuestionPresenter.this.getHandler(), TEST_START_FAILED, paramBundle).sendToTarget();
                    break;
                case TEST_STOP_FAILED:
                    Message.obtain(QuestionPresenter.this.getHandler(), TEST_STOP_FAILED, paramBundle).sendToTarget();
                    break;
            }

        }

        public void onSuccess(int paramInt, Bundle paramBundle)
        {
            switch (paramInt)
            {
                default:
                    break;
                case TEST_START_FAILED:
                    break;
                case TEST_START_SUCCESS:
                    Message.obtain(QuestionPresenter.this.getHandler(), TEST_START_SUCCESS, paramBundle).sendToTarget();
                    break;
                case TEST_STOP_SUCCESS:
                    Message.obtain(QuestionPresenter.this.getHandler(), TEST_STOP_SUCCESS, paramBundle).sendToTarget();
                    break;
            }

        }
    };

    private QuestionPresenter(IQuestionView paramIQuestionView)
    {
        super(paramIQuestionView, new QuestionModel(), false);
        this.mBasicView = paramIQuestionView;
        this.mBasicModel = ((QuestionModel)getBasicModel());
    }

    public static QuestionPresenter getPresenter(IQuestionView paramIQuestionView)
    {
        if (presenter == null)
            presenter = new QuestionPresenter(paramIQuestionView);
        return presenter;
    }

    protected void handleMessage(Message paramMessage)
    {
        super.handleMessage(paramMessage);
        if (this.mBasicView == null)
            return;
        switch (paramMessage.what)
        {
            default:
                break;
            case ServerErrorCode.ERROR_CODE_FAILED:
                this.mBasicView.call(ServerErrorCode.ERROR_CODE_FAILED, (Bundle)paramMessage.obj);
                break;
            case CommonField.MODLE_STATUS_READY:
                this.mBasicView.call(CommonField.MODLE_STATUS_READY, null);
                break;
            case CommonField.MODLE_STATUS_TESTING:
                this.mBasicView.call(CommonField.MODLE_STATUS_TESTING, (Bundle)paramMessage.obj);
                break;
            case CommonField.MODLE_STATUS_FINISH:
                this.mBasicView.call(CommonField.MODLE_STATUS_FINISH, (Bundle)paramMessage.obj);
                break;
            case CommonField.MODLE_STATUS_STOP:
                this.mBasicView.call(CommonField.MODLE_STATUS_STOP, (Bundle)paramMessage.obj);
                break;
            case TEST_START_SUCCESS:
                this.mBasicView.onStartSuccess(TEST_STOP_MSG, (Bundle)paramMessage.obj);
                break;
            case TEST_START_FAILED:
                this.mBasicView.onStartFailed(TEST_STOP_MSG, (Bundle)paramMessage.obj);
                break;
            case TEST_STOP_SUCCESS:
                this.mBasicView.onStopSuccess(TEST_STOP_MSG, (Bundle)paramMessage.obj);
                break;
            case TEST_STOP_FAILED:
                this.mBasicView.onStopFailed(TEST_STOP_MSG, (Bundle)paramMessage.obj);
                break;
        }

    }

    protected void onWorkingCalledOnWorkThread(int paramInt, Bundle paramBundle)
    {
        super.onWorkingCalledOnWorkThread(paramInt, paramBundle);
    }

    public void setmBasicView(IQuestionView paramIQuestionView)
    {
        this.mBasicView = paramIQuestionView;
        this.mBasicModel.setCallback(this.testCallback);
    }

    public void startTest(Context paramContext, List<TestTypeBean> paramList, List<TestParamsBean> paramList1)
    {
        this.mBasicModel.startTest(paramContext, paramList, paramList1, this.testCallback);
    }

    public void startTest(Context paramContext, int testingIndex, List<TestParamsBean> paramList1)
    {
        this.mBasicModel.startTest(paramContext, testingIndex, paramList1, this.testCallback);
    }

    public void stopTest()
    {
        this.mBasicModel.stopTest();
    }
}