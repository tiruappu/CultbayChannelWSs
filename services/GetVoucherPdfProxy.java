package com.cultuzz.services;

public class GetVoucherPdfProxy implements com.cultuzz.services.GetVoucherPdf_PortType {
  private String _endpoint = null;
  private com.cultuzz.services.GetVoucherPdf_PortType getVoucherPdf_PortType = null;
  
  public GetVoucherPdfProxy() {
    _initGetVoucherPdfProxy();
  }
  
  public GetVoucherPdfProxy(String endpoint) {
    _endpoint = endpoint;
    _initGetVoucherPdfProxy();
  }
  
  private void _initGetVoucherPdfProxy() {
    try {
      getVoucherPdf_PortType = (new com.cultuzz.services.GetVoucherPdf_ServiceLocator()).getGetVoucherPdfPort();
      if (getVoucherPdf_PortType != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)getVoucherPdf_PortType)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)getVoucherPdf_PortType)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (getVoucherPdf_PortType != null)
      ((javax.xml.rpc.Stub)getVoucherPdf_PortType)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public com.cultuzz.services.GetVoucherPdf_PortType getGetVoucherPdf_PortType() {
    if (getVoucherPdf_PortType == null)
      _initGetVoucherPdfProxy();
    return getVoucherPdf_PortType;
  }
  
  public java.lang.String generateVoucher(java.lang.String request) throws java.rmi.RemoteException{
    if (getVoucherPdf_PortType == null)
      _initGetVoucherPdfProxy();
    return getVoucherPdf_PortType.generateVoucher(request);
  }
  
  public java.lang.String generateVoucherPreview(java.lang.String request) throws java.rmi.RemoteException{
    if (getVoucherPdf_PortType == null)
      _initGetVoucherPdfProxy();
    return getVoucherPdf_PortType.generateVoucherPreview(request);
  }
  
  
}