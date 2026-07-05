import { useState } from "react";
import { crearEmision } from "../../services/emisionesService";

function FormEmision({ vehiculoId }) {

    const [form, setForm] = useState({

        fechaRevision: "",
        fechaVencimiento: "",
        resultado: ""

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

            await crearEmision({

                vehiculoId: Number(vehiculoId),

                fechaRevision: form.fechaRevision,

                fechaVencimiento: form.fechaVencimiento,

                resultado: form.resultado

            });

            alert("Emisión registrada correctamente.");

            setForm({

                fechaRevision: "",
                fechaVencimiento: "",
                resultado: ""

            });

        } catch (error) {

            console.error(error);

            alert("Error al registrar.");

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

                    <option value="">Seleccione</option>

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
                    Guardar Emisión
                </button>

            </div>

        </div>

    );

}

export default FormEmision;