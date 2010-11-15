package org.opennms.gwt.web.ui.enterprisereporting.client;


import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.NodeList;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.RootPanel;

public class EnterpriseReportingEntryPoint implements EntryPoint {

    public void onModuleLoad() {
        
        NodeList<Element> elementsByTagName = RootPanel.getBodyElement().getElementsByTagName("opennms:enterprisereporting");
        if(elementsByTagName.getLength() > 0) {
            for(int i = 0; i < elementsByTagName.getLength(); i++) {
                Element elem = elementsByTagName.getItem(i);
                if(elem.getId().equals("reportList")) {
                    RootPanel.get(elem.getId()).add(new ReportList());
                }else if(elem.getId().endsWith("addReportBtn")) {
                    Button addBtn = new Button("Add Report");
                    addBtn.addClickHandler(new ClickHandler() {

                        public void onClick(ClickEvent event) {
                            // TODO Auto-generated method stub
                            PopupPanel popup = new PopupPanel();
                            popup.add(new AddReportPopup());
                            popup.show();
                            
                        }
                        
                    });
                    
                    RootPanel.get(elem.getId()).add(addBtn);
                }
            }
            
            
        }
    }

}
