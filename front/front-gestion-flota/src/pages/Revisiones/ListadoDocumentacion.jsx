import { useEffect, useState } from "react";
import "./ListadoDocumentacion.css";
import { listarDocumentacion } from "../../services/documentacionService";

function ListadoDocumentacion() {

    const [vehiculos, setVehiculos] = useState([]);

    useEffect(() => {
        cargarDocumentacion();
    }, []);

    const cargarDocumentacion = async () => {

        try {

            const response = await listarDocumentacion();

            setVehiculos(response.data);

        } catch (error) {

            console.error("Error al cargar la documentación", error);

        }

    };

    const calcularDias = (fecha) => {

        if (!fecha) return null;

        const hoy = new Date();

        const vencimiento = new Date(fecha);

        return Math.ceil(
            (vencimiento - hoy) / (1000 * 60 * 60 * 24)
        );

    };

    const obtenerEstado = (fecha) => {

        if (!fecha) {
            return {
                texto: "Sin registro",
                color: "#e74c3c"
            };
        }

        const dias = calcularDias(fecha);

        if (dias < 0) {
            return {
                texto: "Vencido",
                color: "#e74c3c"
            };
        }

        if (dias <= 30) {
            return {
                texto: `${dias} días`,
                color: "#f39c12"
            };
        }

        return {
            texto: `${dias} días`,
            color: "#27ae60"
        };

    };

    const formatearFecha = (fecha) => {

        if (!fecha) return "Sin registro";

        return new Date(fecha).toLocaleDateString("es-CL");

    };

    const Documento = ({ titulo, icono, documento }) => {

        const estado = obtenerEstado(documento?.fechaVencimiento);

        return (

            <div className="documento-card">

                <div className="documento-titulo">

                    <span>{icono}</span>

                    <span>{titulo}</span>

                </div>

                <div className="documento-fecha">

                    {formatearFecha(documento?.fechaVencimiento)}

                </div>

                <div
                    className="documento-estado"
                    style={{ color: estado.color }}
                >

                    {estado.texto}

                </div>

            </div>

        );

    };

    return (

        <div className="listado-documentacion">

            {

                vehiculos.length === 0 ?

                    <p>No existen vehículos registrados.</p>

                    :

                    vehiculos.map((vehiculo) => (

                        <div
                            className="vehiculo-card"
                            key={vehiculo.id}
                        >

                            <div className="vehiculo-header">

                                <div>

                                    <div className="vehiculo-nombre">

                                        🚛 {vehiculo.marca} {vehiculo.modelo}

                                    </div>

                                    <div className="vehiculo-patente">

                                        {vehiculo.patente}

                                    </div>

                                </div>

                            </div>

                            <div className="documentos-grid">

                                <Documento
                                    icono="📋"
                                    titulo="Revisión Técnica"
                                    documento={vehiculo.revisionTecnica}
                                />

                                <Documento
                                    icono="⛽"
                                    titulo="Emisión Gases"
                                    documento={vehiculo.emisionGases}
                                />

                                <Documento
                                    icono="🛡"
                                    titulo="SOAP"
                                    documento={vehiculo.soap}
                                />

                                <Documento
                                    icono="🚗"
                                    titulo="Permiso"
                                    documento={vehiculo.permisoCirculacion}
                                />

                            </div>

                        </div>

                    ))

            }

        </div>

    );

}

export default ListadoDocumentacion;