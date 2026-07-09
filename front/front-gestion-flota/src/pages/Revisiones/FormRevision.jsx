import { useEffect, useState } from "react";

import {
    crearRevision,
    actualizarRevision
} from "../../services/revisionTecnicaService";

import "./DocumentacionForm.css";

function FormRevision({

    vehiculoId,
    datos = null,
    modo = "crear"

}) {

    const initialForm = {

        fechaRevision: "",
        fechaVencimiento: "",
        resultado: ""

    };

    const [form, setForm] = useState(initialForm);

    const [errores, setErrores] = useState({});

    useEffect(() => {

        if (datos) {

            setForm({

                fechaRevision: datos.fechaRevision || "",
                fechaVencimiento: datos.fechaVencimiento || "",
                resultado: datos.resultado || ""

            });

        } else {

            setForm(initialForm);

        }

        setErrores({});

    }, [datos]);

    const handleChange = (e) => {

        const { name, value } = e.target;

        setForm({

            ...form,
            [name]: value

        });

        setErrores({

            ...errores,
            [name]: ""

        });

    };

    const validar = () => {

        const nuevosErrores = {};

        if (!vehiculoId) {

            nuevosErrores.vehiculo = "Debe seleccionar un vehículo.";

        }

        if (!form.fechaRevision) {

            nuevosErrores.fechaRevision = "La fecha de revisión es obligatoria.";

        }

        if (!form.fechaVencimiento) {

            nuevosErrores.fechaVencimiento = "La fecha de vencimiento es obligatoria.";

        }

        if (

            form.fechaRevision &&
            form.fechaVencimiento &&
            form.fechaVencimiento < form.fechaRevision

        ) {

            nuevosErrores.fechaVencimiento =
                "La fecha de vencimiento debe ser posterior a la revisión.";

        }

        if (!form.resultado) {

            nuevosErrores.resultado =
                "Debe seleccionar un resultado.";

        }

        setErrores(nuevosErrores);

        return Object.keys(nuevosErrores).length === 0;

    };

    const guardar = async () => {

        if (!validar()) return;

        const body = {

            vehiculoId: Number(vehiculoId),

            fechaRevision: form.fechaRevision,

            fechaVencimiento: form.fechaVencimiento,

            resultado: form.resultado

        };

        try {

            if (modo === "editar") {

                if (!datos?.id) {

                    alert("No se encontró la revisión a actualizar.");

                    return;

                }

                await actualizarRevision(datos.id, body);

                alert("Revision Técnica Actualizada");

            } else {

                await crearRevision(body);

                alert("Revisión técnica registrada correctamente.");

                setForm(initialForm);

            }

            setErrores({});

        } catch (error) {

            console.error(error);

            alert(

                error.response?.data?.message ||

                "Ocurrió un error al guardar."

            );

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

                {errores.fechaRevision && (

                    <span className="error">

                        {errores.fechaRevision}

                    </span>

                )}

            </div>

            <div className="campo">

                <label>Fecha Vencimiento</label>

                <input
                    type="date"
                    name="fechaVencimiento"
                    value={form.fechaVencimiento}
                    onChange={handleChange}
                />

                {errores.fechaVencimiento && (

                    <span className="error">

                        {errores.fechaVencimiento}

                    </span>

                )}

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

                {errores.resultado && (

                    <span className="error">

                        {errores.resultado}

                    </span>

                )}

            </div>

            {errores.vehiculo && (

                <span className="error">

                    {errores.vehiculo}

                </span>

            )}

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