import { useState } from "react";
import { crearSoap } from "../../services/soapService";

function FormSoap({ vehiculoId }) {

    const [form, setForm] = useState({

        aseguradora: "",
        numeroPoliza: "",
        fechaEmision: "",
        fechaVencimiento: ""

    });

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

        try {

            await crearSoap({

                vehiculoId: Number(vehiculoId),

                aseguradora: form.aseguradora,

                numeroPoliza: form.numeroPoliza,

                fechaEmision: form.fechaEmision,

                fechaVencimiento: form.fechaVencimiento

            });

            alert("SOAP registrado correctamente.");

            setForm({

                aseguradora: "",
                numeroPoliza: "",
                fechaEmision: "",
                fechaVencimiento: ""

            });

        } catch (error) {

            console.error(error);

            alert("Error al registrar.");

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

            </div>

            <div className="campo">

                <label>Número Póliza</label>

                <input
                    type="text"
                    name="numeroPoliza"
                    value={form.numeroPoliza}
                    onChange={handleChange}
                />

            </div>

            <div className="campo">

                <label>Fecha Emisión</label>

                <input
                    type="date"
                    name="fechaEmision"
                    value={form.fechaEmision}
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

            <div className="acciones">

                <button
                    className="btn-guardar"
                    onClick={guardar}
                >
                    Guardar SOAP
                </button>

            </div>

        </div>

    );

}

export default FormSoap;