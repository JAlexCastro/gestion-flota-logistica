import { useEffect, useState } from "react";

import { listarVehiculos } from "../../services/vehiculoService";
import { listarDocumentacion } from "../../services/documentacionService";

import FormRevision from "./FormRevision";
import FormEmision from "./FormEmision";
import FormSoap from "./FormSoap";
import FormPermiso from "./FormPermiso";

import "./RegistrarDocumentacion.css";

function RegistrarDocumentacion() {

    const [vehiculos, setVehiculos] = useState([]);

    const [vehiculoId, setVehiculoId] = useState("");

    const [tab, setTab] = useState("revision");

    const [documentacion, setDocumentacion] = useState(null);

    useEffect(() => {

        cargarVehiculos();

    }, []);

    const cargarVehiculos = async () => {

        try {

            const response = await listarVehiculos();

            setVehiculos(response.data);

        } catch (error) {

            console.error(error);

        }

    };

    const seleccionarVehiculo = async (id) => {

        setVehiculoId(id);

        if (!id) {

            setDocumentacion(null);

            return;

        }

        try {

            const response = await listarDocumentacion();

            const doc = response.data.find(

                x => x.id === Number(id)

            );

            setDocumentacion(doc || {});

        } catch (error) {

            console.error(error);

        }

    };

    return (

        <div className="registrar-container">

            <h2>

                Registrar Documentación

            </h2>

            <div className="campo">

                <label>

                    Vehículo

                </label>

                <select

                    value={vehiculoId}

                    onChange={(e) => seleccionarVehiculo(e.target.value)}

                >

                    <option value="">

                        Seleccione un vehículo

                    </option>

                    {

                        vehiculos.map((v) => (

                            <option

                                key={v.id}

                                value={v.id}

                            >

                                {v.nombre} - {v.patente}

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

                tab === "revision" && (

                    documentacion?.revisionTecnica ?

                        <p className="mensaje-existe">

                            Este vehículo ya tiene una revisión técnica registrada.
                            Utilice la opción <b>Actualizar Documentación</b>.

                        </p>

                    :

                        <FormRevision
                            vehiculoId={vehiculoId}
                        />

                )

            }

            {

                tab === "emision" && (

                    documentacion?.emisionGases ?

                        <p className="mensaje-existe">

                            Este vehículo ya tiene una emisión de gases registrada.
                            Utilice la opción <b>Actualizar Documentación</b>.

                        </p>

                    :

                        <FormEmision
                            vehiculoId={vehiculoId}
                        />

                )

            }

            {

                tab === "soap" && (

                    documentacion?.soap ?

                        <p className="mensaje-existe">

                            Este vehículo ya tiene un SOAP registrado.
                            Utilice la opción <b>Actualizar Documentación</b>.

                        </p>

                    :

                        <FormSoap
                            vehiculoId={vehiculoId}
                        />

                )

            }

            {

                tab === "permiso" && (

                    documentacion?.permisoCirculacion ?

                        <p className="mensaje-existe">

                            Este vehículo ya tiene un permiso de circulación registrado.
                            Utilice la opción <b>Actualizar Documentación</b>.

                        </p>

                    :

                        <FormPermiso
                            vehiculoId={vehiculoId}
                        />

                )

            }

        </div>

    );

}

export default RegistrarDocumentacion;