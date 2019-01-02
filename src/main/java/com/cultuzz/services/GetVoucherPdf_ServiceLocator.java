/**
 * GetVoucherPdf_ServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.cultuzz.services;

public class GetVoucherPdf_ServiceLocator extends org.apache.axis.client.Service implements com.cultuzz.services.GetVoucherPdf_Service {

    public GetVoucherPdf_ServiceLocator() {
    }


    public GetVoucherPdf_ServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public GetVoucherPdf_ServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for GetVoucherPdfPort
    //Localhost
    //private java.lang.String GetVoucherPdfPort_address = "http://localhost:8081/VoucherService/GetVoucherPdf";
    //Test
    //private java.lang.String GetVoucherPdfPort_address = "http://10.246.225.81:8090/VoucherService/GetVoucherPdf";
    //Live
    private java.lang.String GetVoucherPdfPort_address = "http://10.246.225.221:8090/VoucherService/GetVoucherPdf";
    public java.lang.String getGetVoucherPdfPortAddress() {
        return GetVoucherPdfPort_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String GetVoucherPdfPortWSDDServiceName = "GetVoucherPdfPort";

    public java.lang.String getGetVoucherPdfPortWSDDServiceName() {
        return GetVoucherPdfPortWSDDServiceName;
    }

    public void setGetVoucherPdfPortWSDDServiceName(java.lang.String name) {
        GetVoucherPdfPortWSDDServiceName = name;
    }

    public com.cultuzz.services.GetVoucherPdf_PortType getGetVoucherPdfPort() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(GetVoucherPdfPort_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getGetVoucherPdfPort(endpoint);
    }

    public com.cultuzz.services.GetVoucherPdf_PortType getGetVoucherPdfPort(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            com.cultuzz.services.GetVoucherPdfPortBindingStub _stub = new com.cultuzz.services.GetVoucherPdfPortBindingStub(portAddress, this);
            _stub.setPortName(getGetVoucherPdfPortWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setGetVoucherPdfPortEndpointAddress(java.lang.String address) {
        GetVoucherPdfPort_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (com.cultuzz.services.GetVoucherPdf_PortType.class.isAssignableFrom(serviceEndpointInterface)) {
                com.cultuzz.services.GetVoucherPdfPortBindingStub _stub = new com.cultuzz.services.GetVoucherPdfPortBindingStub(new java.net.URL(GetVoucherPdfPort_address), this);
                _stub.setPortName(getGetVoucherPdfPortWSDDServiceName());
                return _stub;
            }
        }
        catch (java.lang.Throwable t) {
            throw new javax.xml.rpc.ServiceException(t);
        }
        throw new javax.xml.rpc.ServiceException("There is no stub implementation for the interface:  " + (serviceEndpointInterface == null ? "null" : serviceEndpointInterface.getName()));
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(javax.xml.namespace.QName portName, Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        if (portName == null) {
            return getPort(serviceEndpointInterface);
        }
        java.lang.String inputPortName = portName.getLocalPart();
        if ("GetVoucherPdfPort".equals(inputPortName)) {
            return getGetVoucherPdfPort();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://services.cultuzz.com/", "GetVoucherPdf");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://services.cultuzz.com/", "GetVoucherPdfPort"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("GetVoucherPdfPort".equals(portName)) {
            setGetVoucherPdfPortEndpointAddress(address);
        }
        else 
{ // Unknown Port Name
            throw new javax.xml.rpc.ServiceException(" Cannot set Endpoint Address for Unknown Port" + portName);
        }
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(javax.xml.namespace.QName portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        setEndpointAddress(portName.getLocalPart(), address);
    }

}
