import { useState, useEffect } from "react";
import {
    crearPermiso,
    actualizarPermiso
} from "../../services/permisoCirculacionService";

import "./DocumentacionForm.css";

function FormPermiso({ vehiculoId, datos, modo}) {

    const initialForm = {

        fechaEmision: "",
        fechaVencimiento: "",
        estado: ""

    };

    const [form, setForm] = useState(initialForm);

    const [errores, setErrores] = useState({});

    useEffect(() => {

    if (modo === "editar" && datos) {
        setForm({
            fechaEmision: datos.fechaEmision || "",
            fechaVencimiento: datos.fechaVencimiento || "",
            estado: datos.estado || ""
        });
        }
    }, [datos, modo]);

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

        if (!form.fechaEmision) {

            nuevosErrores.fechaEmision =
                "Seleccione la fecha de emisión.";

        }

        if (!form.fechaVencimiento) {

            nuevosErrores.fechaVencimiento =
                "Seleccione la fecha de vencimiento.";

        }

        if (

            form.fechaEmision &&
            form.fechaVencimiento &&
            form.fechaVencimiento < form.fechaEmision

        ) {

            nuevosErrores.fechaVencimiento =
                "La fecha de vencimiento debe ser posterior a la fecha de emisión.";

        }

        if (!form.estado) {

            nuevosErrores.estado =
                "Seleccione un estado.";

        }

        setErrores(nuevosErrores);

        return Object.keys(nuevosErrores).length === 0;

    };

    const guardar = async () => {

        if (!validar()) return;

        try {

            const body = {
                vehiculoId: Number(vehiculoId),
                fechaEmision: form.fechaEmision,
                fechaVencimiento: form.fechaVencimiento,
                estado: form.estado
            };

            if (modo === "editar") {
                await actualizarPermiso(datos.id, body);
                alert("Permiso actualizado correctamente.");

            } else {
                await crearPermiso(body);
                alert("Permiso registrado correctamente.");

            }
        } catch (error) {
            console.error(error);
            alert(
                error.message ||
                "Ocurrió un error."
            );
        }

    };

    return (

        <div className="form-documento">

            <div className="campo">

                <label>Fecha Emisión</label>

                <input
                    type="date"
                    name="fechaEmision"
                    value={form.fechaEmision}
                    onChange={handleChange}
                />

                {errores.fechaEmision &&

                    <span className="error">

                        {errores.fechaEmision}

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

                <label>Estado</label>

                <select
                    name="estado"
                    value={form.estado}
                    onChange={handleChange}
                >

                    <option value="">
                        Seleccione
                    </option>

                    <option value="VIGENTE">
                        Vigente
                    </option>

                    <option value="VENCIDO">
                        Vencido
                    </option>

                </select>

                {errores.estado &&

                    <span className="error">

                        {errores.estado}

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
                    ? "Actualizar Permiso"
                    : "Guardar Permiso"}

                </button>

            </div>

        </div>

    );

}

export default FormPermiso;