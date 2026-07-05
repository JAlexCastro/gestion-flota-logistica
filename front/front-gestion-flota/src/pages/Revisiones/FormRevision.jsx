import { useState } from "react";
import { crearRevision } from "../../services/revisionTecnicaService";

import { useEffect, useState } from "react";

import {
    crearRevision,
    actualizarRevision
} from "../../services/revisionTecnicaService";

function FormRevision({

    vehiculoId,

    datos = null,

    modo = "crear"

}) {

    const [form, setForm] = useState({
        fechaRevision: "",
        fechaVencimiento: "",
        resultado: ""
    });

    useEffect(() => {

    if (datos) {

        setForm({

            fechaRevision: datos.fechaRevision || "",

            fechaVencimiento: datos.fechaVencimiento || "",

            resultado: datos.resultado || ""

        });

    }

    }, [datos]);

    const handleChange = (e) => {

        setForm({
            ...form,
            [e.target.name]: e.target.value
        });

    };

    const guardar = async () => {

    if (!vehiculoId) {

        alert("Seleccione un vehículo.");

        return;

    }

    const body = {

        vehiculoId: Number(vehiculoId),

        fechaRevision: form.fechaRevision,

        fechaVencimiento: form.fechaVencimiento,

        resultado: form.resultado

    };

    try {

        if (modo === "editar") {

            await actualizarRevision(datos.id, body);

            alert("Revisión actualizada.");

        } else {

            await crearRevision(body);

            alert("Revisión registrada.");

        }

    } catch (error) {

        console.error(error);

        alert("Ocurrió un error.");

    }

    };
    return (

        <div className="form-documento">

            <div className="campo">

                <label>Fecha Revisión</label>

                <input
                    type="date"
                    name="fechaRevision"
                    value={form.fechaRevision}
                    onChange={handleChange}
                />

            </div>

            <div className="campo">

                <label>Fecha Vencimiento</label>

                <input
                    type="date"
                    name="fechaVencimiento"
                    value={form.fechaVencimiento}
                    onChange={handleChange}
                />

            </div>

            <div className="campo">

                <label>Resultado</label>

                <select
                    name="resultado"
                    value={form.resultado}
                    onChange={handleChange}
                >

                    <option value="">
                        Seleccione
                    </option>

                    <option value="APROBADO">
                        Aprobado
                    </option>

                    <option value="RECHAZADO">
                        Rechazado
                    </option>

                </select>

            </div>

            <div className="acciones">

                <button
                    className="btn-guardar"
                    onClick={guardar}
                >

                    {
                        modo === "editar"

                            ? "Actualizar Revisión"

                            : "Guardar Revisión"
                    }

                </button>

            </div>

        </div>

    );

}

export default FormRevision;