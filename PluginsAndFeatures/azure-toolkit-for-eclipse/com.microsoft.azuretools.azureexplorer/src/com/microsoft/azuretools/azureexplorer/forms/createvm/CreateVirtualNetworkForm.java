package com.microsoft.azuretools.azureexplorer.forms.createvm;

import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import com.microsoft.azuretools.core.components.AzureTitleAreaDialogWrapper;
import com.microsoft.azuretools.core.utils.PluginUtil;
import com.microsoft.tooling.msservices.components.DefaultLoader;
import com.microsoft.tooling.msservices.model.vm.VirtualNetwork;

public class CreateVirtualNetworkForm extends AzureTitleAreaDialogWrapper {
	private Text nameField;
    private Text addressSpaceField;
    private Text subnetNameField;
    private Text subnetAddressRangeField;
    
    private String vmName;
    
    private Runnable onCreate;
    private VirtualNetwork network;
    
	public CreateVirtualNetworkForm(String vmName) {
		super(PluginUtil.getParentShell());
		this.vmName = vmName;
	}
	
	@Override
    protected void configureShell(Shell newShell) {
        super.configureShell(newShell);
        newShell.setText("Create New Virtual Network");
    }
    
    @Override
    protected Control createDialogArea(Composite parent) {
		setTitle("Create New Virtual Network");
//		setMessage("Create New Virtual Network");
		setHelpAvailable(false);
    	
        Composite container = new Composite(parent, SWT.NONE);
        GridLayout gridLayout = new GridLayout();
        gridLayout.numColumns = 1;
        container.setLayout(gridLayout);
        GridData gridData = new GridData();
        gridData.widthHint = 250;
        gridData.horizontalAlignment = SWT.FILL;
        gridData.grabExcessHorizontalSpace = true;
        container.setLayoutData(gridData);

        Label nameLabel = new Label(container, SWT.LEFT);
        nameLabel.setText("Name:");
        gridData = new GridData();
        nameField = new Text(container, SWT.BORDER);
        gridData = new GridData();
        gridData.horizontalAlignment = SWT.FILL;
        gridData.grabExcessHorizontalSpace = true;
        nameField.setLayoutData(gridData);
        nameField.setText(vmName + "-vnet");
        
        Label addressSpaceLabel = new Label(container, SWT.LEFT);
        addressSpaceLabel.setText("Address Space:");
        gridData = new GridData();
        addressSpaceField = new Text(container, SWT.BORDER);
        gridData = new GridData();
        gridData.horizontalAlignment = SWT.FILL;
        gridData.grabExcessHorizontalSpace = true;
        addressSpaceField.setLayoutData(gridData);
        addressSpaceField.setText("10.0.2.0/24");
        Label addressSpaceHint = new Label(container, SWT.LEFT);
        addressSpaceHint.setText("10.0.2.0 - 10.0.2.255 (256 addresses)");		
        
        Label subnetNameLabel = new Label(container, SWT.LEFT);
        subnetNameLabel.setText("Subnet Name:");
        subnetNameField = new Text(container, SWT.BORDER);
        gridData = new GridData();
        gridData.horizontalAlignment = SWT.FILL;
        gridData.grabExcessHorizontalSpace = true;
        subnetNameField.setLayoutData(gridData);
        subnetNameField.setText("default");

        Label subnetAddressRangeLabel = new Label(container, SWT.LEFT);
        subnetAddressRangeLabel.setText("Subnet Address Range:");
        subnetAddressRangeField = new Text(container, SWT.BORDER);
        gridData = new GridData();
        gridData.horizontalAlignment = SWT.FILL;
        gridData.grabExcessHorizontalSpace = true;
        subnetAddressRangeField.setLayoutData(gridData);
        subnetAddressRangeField.setText("10.0.2.0/24");
        
        return super.createDialogArea(parent);
    }

    @Override
    protected void okPressed() {
    	network = new VirtualNetwork(nameField.getText().trim(), addressSpaceField.getText().trim(), subnetNameField.getText().trim(),
                subnetAddressRangeField.getText().trim());
    	if (onCreate != null) {
    		DefaultLoader.getIdeHelper().invokeLater(onCreate);
    	}
        super.okPressed();
    }

	public VirtualNetwork getNetwork() {
		return network;
	}

	public void setOnCreate(Runnable onCreate) {
		this.onCreate = onCreate;
	}
}