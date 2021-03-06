//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vJAXB 2.1.10 in JDK 6 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2009.10.15 at 02:42:45 PM GMT-03:00 
//


package gil.jr.ecf;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="data-emissao" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ie" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="cnpj" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="rs" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="end" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="municipio" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="equipamentos">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="ecf" maxOccurs="unbounded">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="marca" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                             &lt;element name="modelo" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                             &lt;element name="nrato" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                             &lt;element name="nrpedido" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                             &lt;element name="nrfab" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                             &lt;element name="nruso-cessacao" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                           &lt;/sequence>
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "dataEmissao",
    "ie",
    "cnpj",
    "rs",
    "end",
    "municipio",
    "equipamentos"
})
@XmlRootElement(name = "relatorio-sitafe")
public class RelatorioSitafe {

    @XmlElement(name = "data-emissao", required = true)
    protected String dataEmissao;
    @XmlElement(required = true)
    protected String ie;
    @XmlElement(required = true)
    protected String cnpj;
    @XmlElement(required = true)
    protected String rs;
    @XmlElement(required = true)
    protected String end;
    @XmlElement(required = true)
    protected String municipio;
    @XmlElement(required = true)
    protected RelatorioSitafe.Equipamentos equipamentos;

    /**
     * Gets the value of the dataEmissao property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDataEmissao() {
        return dataEmissao;
    }

    /**
     * Sets the value of the dataEmissao property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDataEmissao(String value) {
        this.dataEmissao = value;
    }

    /**
     * Gets the value of the ie property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIe() {
        return ie;
    }

    /**
     * Sets the value of the ie property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIe(String value) {
        this.ie = value;
    }

    /**
     * Gets the value of the cnpj property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCnpj() {
        return cnpj;
    }

    /**
     * Sets the value of the cnpj property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCnpj(String value) {
        this.cnpj = value;
    }

    /**
     * Gets the value of the rs property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRs() {
        return rs;
    }

    /**
     * Sets the value of the rs property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRs(String value) {
        this.rs = value;
    }

    /**
     * Gets the value of the end property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEnd() {
        return end;
    }

    /**
     * Sets the value of the end property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEnd(String value) {
        this.end = value;
    }

    /**
     * Gets the value of the municipio property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMunicipio() {
        return municipio;
    }

    /**
     * Sets the value of the municipio property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMunicipio(String value) {
        this.municipio = value;
    }

    /**
     * Gets the value of the equipamentos property.
     * 
     * @return
     *     possible object is
     *     {@link RelatorioSitafe.Equipamentos }
     *     
     */
    public RelatorioSitafe.Equipamentos getEquipamentos() {
        return equipamentos;
    }

    /**
     * Sets the value of the equipamentos property.
     * 
     * @param value
     *     allowed object is
     *     {@link RelatorioSitafe.Equipamentos }
     *     
     */
    public void setEquipamentos(RelatorioSitafe.Equipamentos value) {
        this.equipamentos = value;
    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="ecf" maxOccurs="unbounded">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="marca" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                   &lt;element name="modelo" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                   &lt;element name="nrato" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                   &lt;element name="nrpedido" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                   &lt;element name="nrfab" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                   &lt;element name="nruso-cessacao" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                 &lt;/sequence>
     *               &lt;/restriction>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *       &lt;/sequence>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "ecf"
    })
    public static class Equipamentos {

        @XmlElement(required = true)
        protected List<RelatorioSitafe.Equipamentos.Ecf> ecf;

        /**
         * Gets the value of the ecf property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the ecf property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getEcf().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link RelatorioSitafe.Equipamentos.Ecf }
         * 
         * 
         */
        public List<RelatorioSitafe.Equipamentos.Ecf> getEcf() {
            if (ecf == null) {
                ecf = new ArrayList<RelatorioSitafe.Equipamentos.Ecf>();
            }
            return this.ecf;
        }


        /**
         * <p>Java class for anonymous complex type.
         * 
         * <p>The following schema fragment specifies the expected content contained within this class.
         * 
         * <pre>
         * &lt;complexType>
         *   &lt;complexContent>
         *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *       &lt;sequence>
         *         &lt;element name="marca" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *         &lt;element name="modelo" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *         &lt;element name="nrato" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *         &lt;element name="nrpedido" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *         &lt;element name="nrfab" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *         &lt;element name="nruso-cessacao" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *       &lt;/sequence>
         *     &lt;/restriction>
         *   &lt;/complexContent>
         * &lt;/complexType>
         * </pre>
         * 
         * 
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
            "marca",
            "modelo",
            "nrato",
            "nrpedido",
            "nrfab",
            "nrusoCessacao"
        })
        public static class Ecf {

            @XmlElement(required = true)
            protected String marca;
            @XmlElement(required = true)
            protected String modelo;
            @XmlElement(required = true)
            protected String nrato;
            @XmlElement(required = true)
            protected String nrpedido;
            @XmlElement(required = true)
            protected String nrfab;
            @XmlElement(name = "nruso-cessacao", required = true)
            protected String nrusoCessacao;

            /**
             * Gets the value of the marca property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getMarca() {
                return marca;
            }

            /**
             * Sets the value of the marca property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setMarca(String value) {
                this.marca = value;
            }

            /**
             * Gets the value of the modelo property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getModelo() {
                return modelo;
            }

            /**
             * Sets the value of the modelo property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setModelo(String value) {
                this.modelo = value;
            }

            /**
             * Gets the value of the nrato property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getNrato() {
                return nrato;
            }

            /**
             * Sets the value of the nrato property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setNrato(String value) {
                this.nrato = value;
            }

            /**
             * Gets the value of the nrpedido property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getNrpedido() {
                return nrpedido;
            }

            /**
             * Sets the value of the nrpedido property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setNrpedido(String value) {
                this.nrpedido = value;
            }

            /**
             * Gets the value of the nrfab property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getNrfab() {
                return nrfab;
            }

            /**
             * Sets the value of the nrfab property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setNrfab(String value) {
                this.nrfab = value;
            }

            /**
             * Gets the value of the nrusoCessacao property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getNrusoCessacao() {
                return nrusoCessacao;
            }

            /**
             * Sets the value of the nrusoCessacao property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setNrusoCessacao(String value) {
                this.nrusoCessacao = value;
            }

        }

    }

}
