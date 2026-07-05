import { useEffect, useState } from "react";

import { listarVehiculos } from "../../services/vehiculoService";
import { listarDocumentacion } from "../../services/documentacionService";

import FormRevision from "./FormRevision";
import FormEmision from "./FormEmision";
import FormSoap from "./FormSoap";
import FormPermiso from "./FormPermiso";

import "./RegistrarDocumentacion.css";

function ActualizarDocumentacion() {

    const [vehiculos, setVehiculos] = useState([]);

    const [vehiculoId, setVehiculoId] = useState("");

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

            setDocumentacion(doc);

        } catch (error) {

            console.error(error);

        }

    };

    return (

        <div className="registrar-container">

            <h2>

                Actualizar Documentación

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

                        vehiculos.map((vehiculo) => (

                            <option

                                key={vehiculo.id}

                                value={vehiculo.id}

                            >

                                {vehiculo.marca} {vehiculo.modelo} - {vehiculo.patente}

                            </option>

                        ))

                    }

                </select>

            </div>

            {

                documentacion &&

                <>

                    <div className="tabs">

                        <button>

                            Revisión Técnica

                        </button>

                    </div>

                    <FormRevision

                        vehiculoId={vehiculoId}

                        datos={documentacion.revisionTecnica}

                        modo="editar"

                    />

                    <hr />

                    <div className="tabs">

                        <button>

                            Emisión de Gases

                        </button>

                    </div>

                    <FormEmision

                        vehiculoId={vehiculoId}

                        datos={documentacion.emisionGases}

                        modo="editar"

                    />

                    <hr />

                    <div className="tabs">

                        <button>

                            SOAP

                        </button>

                    </div>

                    <FormSoap

                        vehiculoId={vehiculoId}

                        datos={documentacion.soap}

                        modo="editar"

                    />

                    <hr />

                    <div className="tabs">

                        <button>

                            Permiso de Circulación

                        </button>

                    </div>

                    <FormPermiso

                        vehiculoId={vehiculoId}

                        datos={documentacion.permisoCirculacion}

                        modo="editar"

                    />

                </>

            }

        </div>

    );

}

export default ActualizarDocumentacion;