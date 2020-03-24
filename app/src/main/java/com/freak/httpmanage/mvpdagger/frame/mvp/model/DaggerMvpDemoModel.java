package com.freak.httpmanage.mvpdagger.frame.mvp.model;

import com.freak.httphelper.lifecycle.IRepositoryManager;
import com.freak.httphelper.mvp.BaseDaggerModel;
import com.freak.httpmanage.mvpdagger.frame.mvp.contract.DaggerMvpDemoContract;

public class DaggerMvpDemoModel extends BaseDaggerModel implements DaggerMvpDemoContract.Model {
    public DaggerMvpDemoModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }
}
