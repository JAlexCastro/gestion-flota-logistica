import { useEffect, useState } from "react";
import { crearEmision, actualizarEmision } from "../../services/emisionesService";

import "./DocumentacionForm.css";

function FormEmision({ vehiculoId, datos = null,  modo = "crear"}) 
{

    const initialForm = {

        fechaRevision: "",
        fechaVencimiento: "",
        resultado: ""

    };

    const [form, setForm] = useState(initialForm);

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

    const [errores, setErrores] = useState({});

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

            nuevosErrores.vehiculo =
                "Debe seleccionar un vehículo.";

        }

        if (!form.fechaRevision) {

            nuevosErrores.fechaRevision =
                "Seleccione la fecha de revisión.";

        }

        if (!form.fechaVencimiento) {

            nuevosErrores.fechaVencimiento =
                "Seleccione la fecha de vencimiento.";

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
                "Seleccione un resultado.";

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

                alert("No se encontró la emisión.");

                return;

            }

            await actualizarEmision(datos.id, body);

            alert("Emisión actualizada correctamente.");

        } else {

            await crearEmision(body);

            alert("Emisión registrada correctamente.");

            setForm(initialForm);

        }

        setErrores({});

    } catch (error) {

        console.error(error);

        alert(

            error.response?.data?.message ||

            "Ocurrió un error al guardar la emisión."

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

                {errores.fechaRevision &&

                    <span className="error">

                        {errores.fechaRevision}

                    </span>

                }

            </div>

            <div className="campo">

                <label>Fecha Vencimiento</label>

                <input
                    type="date"
                    name="fechaVencimiento"
                    value={form.fechaVencimiento}
                    onChange={handleChange}
                />

                {errores.fechaVencimiento &&

                    <span className="error">

                        {errores.fechaVencimiento}

                    </span>

                }

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

                {errores.resultado &&

                    <span className="error">

                        {errores.resultado}

                    </span>

                }

            </div>

            {errores.vehiculo &&

                <span className="error">

                    {errores.vehiculo}

                </span>

            }

            <div className="acciones">

                <button
                    className="btn-guardar"
                    onClick={guardar}
                >


                    {modo === "editar"
                    ? "Actualizar Emisión"
                    : "Guardar Emisión"}

                </button>

            </div>

        </div>

    );

}

export default FormEmision;