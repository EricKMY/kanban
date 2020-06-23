package domain.adapter.presenter.workflow.create;

import domain.usecase.workflow.createWorkflow.CreateWorkflowOutput;

public class CreateWorkflowPresenter implements CreateWorkflowOutput {
    private String workflowId;

    public CreateWorkflowViewModel build(){
        CreateWorkflowViewModel createWorkflowViewModel = new CreateWorkflowViewModel();
        createWorkflowViewModel.setWorkflowId(workflowId);
        return createWorkflowViewModel;
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
