import { useEffect, useState } from "react";

import {
    crearSoap,
    actualizarSoap
} from "../../services/soapService";

import "./DocumentacionForm.css";

function FormSoap({

    vehiculoId,

    datos = null,

    modo = "crear"

}) {

    const initialForm = {

        aseguradora: "",
        numeroPoliza: "",
        fechaEmision: "",
        fechaVencimiento: ""

    };

    const [form, setForm] = useState(initialForm);

    const [errores, setErrores] = useState({});

    useEffect(() => {

        if (datos) {

            setForm({

                aseguradora: datos.aseguradora || "",

                numeroPoliza: datos.numeroPoliza || "",

                fechaEmision: datos.fechaEmision || "",

                fechaVencimiento: datos.fechaVencimiento || ""

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

            nuevosErrores.vehiculo =
                "Debe seleccionar un vehículo.";

        }

        if (!form.aseguradora.trim()) {

            nuevosErrores.aseguradora =
                "Ingrese la aseguradora.";

        } else if (form.aseguradora.trim().length < 3) {

            nuevosErrores.aseguradora =
                "Debe tener al menos 3 caracteres.";

        }

        if (!form.numeroPoliza.trim()) {

            nuevosErrores.numeroPoliza =
                "Ingrese el número de póliza.";

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
                "La fecha de vencimiento debe ser posterior a la emisión.";

        }

        setErrores(nuevosErrores);

        return Object.keys(nuevosErrores).length === 0;

    };

    const guardar = async () => {

        if (!validar()) return;

        const body = {

            vehiculoId: Number(vehiculoId),

            aseguradora: form.aseguradora.trim(),

            numeroPoliza: form.numeroPoliza.trim(),

            fechaEmision: form.fechaEmision,

            fechaVencimiento: form.fechaVencimiento

        };

        try {

            if (modo === "editar") {

                if (!datos?.id) {

                    alert("No se encontró el SOAP.");

                    return;

                }

                await actualizarSoap(datos.id, body);

                alert("SOAP actualizado correctamente.");

            } else {

                await crearSoap(body);

                alert("SOAP registrado correctamente.");

                setForm(initialForm);

            }

            setErrores({});

        } catch (error) {

            console.error(error);

            alert(

                error.response?.data?.message ||

                "Ocurrió un error."

            );

        }

    };

    return (

        <div className="form-documento">

            <div className="campo">

                <label>Aseguradora</label>

                <input
                    type="text"
                    name="aseguradora"
                    value={form.aseguradora}
                    onChange={handleChange}
                />

                {errores.aseguradora &&

                    <span className="error">

                        {errores.aseguradora}

                    </span>

                }

            </div>

            <div className="campo">

                <label>Número Póliza</label>

                <input
                    type="text"
                    name="numeroPoliza"
                    value={form.numeroPoliza}
                    onChange={handleChange}
                />

                {errores.numeroPoliza &&

                    <span className="error">

                        {errores.numeroPoliza}

                    </span>

                }

            </div>

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

                    {

                        modo === "editar"

                            ? "Actualizar SOAP"

                            : "Guardar SOAP"

                    }

                </button>

            </div>

        </div>

    );

}

export default FormSoap;