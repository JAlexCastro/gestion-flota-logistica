import { useEffect, useState } from "react";

import { listarVehiculos } from "../../services/vehiculoService";

import FormRevision from "./FormRevision";
import FormEmision from "./FormEmision";
import FormSoap from "./FormSoap";
import FormPermiso from "./FormPermiso";

import "./RegistrarDocumentacion.css";

function RegistrarDocumentacion() {

    const [vehiculos, setVehiculos] = useState([]);

    const [vehiculoId, setVehiculoId] = useState("");

    const [tab, setTab] = useState("revision");

    useEffect(() => {

        cargarVehiculos();

    }, []);

    const cargarVehiculos = async () => {

        const response = await listarVehiculos();

        setVehiculos(response.data);

    };

    return (

        <div className="registrar-container">

            <h2>Registrar Documentación</h2>

            <div className="campo">

                <label>Vehículo</label>

                <select
                    value={vehiculoId}
                    onChange={(e) => setVehiculoId(e.target.value)}
                >

                    <option value="">
                        Seleccione un vehículo
                    </option>

                    {

                        vehiculos.map(v => (

                            <option
                                key={v.id}
                                value={v.id}
                            >

                                {v.marca} {v.modelo} - {v.patente}

                            </option>

                        ))

                    }

                </select>

            </div>

            <div className="tabs">

                <button
                    className={tab === "revision" ? "active" : ""}
                    onClick={() => setTab("revision")}
                >
                    Revisión Técnica
                </button>

                <button
                    className={tab === "emision" ? "active" : ""}
                    onClick={() => setTab("emision")}
                >
                    Emisión
                </button>

                <button
                    className={tab === "soap" ? "active" : ""}
                    onClick={() => setTab("soap")}
                >
                    SOAP
                </button>

                <button
                    className={tab === "permiso" ? "active" : ""}
                    onClick={() => setTab("permiso")}
                >
                    Permiso
                </button>

            </div>

            {

                tab === "revision" &&

                <FormRevision
                    vehiculoId={vehiculoId}
                />

            }

            {

                tab === "emision" &&

                <FormEmision
                    vehiculoId={vehiculoId}
                />

            }

            {

                tab === "soap" &&

                <FormSoap
                    vehiculoId={vehiculoId}
                />

            }

            {

                tab === "permiso" &&

                <FormPermiso
                    vehiculoId={vehiculoId}
                />

            }

        </div>

    );

}

export default RegistrarDocumentacion;