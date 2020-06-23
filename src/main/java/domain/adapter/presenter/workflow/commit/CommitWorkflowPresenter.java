package domain.adapter.presenter.workflow.commit;

import domain.usecase.workflow.commitWorkflow.CommitWorkflowOutput;

public class CommitWorkflowPresenter implements CommitWorkflowOutput {
    private String workflowId;

    public CommitWorkflowViewModel build(){
        CommitWorkflowViewModel commitWorkflowViewModel = new CommitWorkflowViewModel();
        commitWorkflowViewModel.setWorkflowId(workflowId);
        return commitWorkflowViewModel;
    }

    @Override
    public String getWorkflowId() {
        return workflowId;
    }

    @Override
    public void setWorkflowId(String workflowId) {
        this.workflowId = workflowId;
    }
}
