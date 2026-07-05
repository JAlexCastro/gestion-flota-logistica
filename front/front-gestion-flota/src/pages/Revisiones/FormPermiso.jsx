import { useState } from "react";
import { crearPermiso } from "../../services/permisoCirculacionService";

function FormPermiso({ vehiculoId }) {

    const [form, setForm] = useState({

        fechaEmision: "",
        fechaVencimiento: "",
        estado: ""

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

            await crearPermiso({

                vehiculoId: Number(vehiculoId),

                fechaEmision: form.fechaEmision,

                fechaVencimiento: form.fechaVencimiento,

                estado: form.estado

            });

            alert("Permiso registrado correctamente.");

            setForm({

                fechaEmision: "",
                fechaVencimiento: "",
                estado: ""

            });

        } catch (error) {

            console.error(error);

            alert("Error al registrar.");

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

                <label>Estado</label>

                <select
                    name="estado"
                    value={form.estado}
                    onChange={handleChange}
                >

                    <option value="">Seleccione</option>

                    <option value="VIGENTE">
                        Vigente
                    </option>

                    <option value="VENCIDO">
                        Vencido
                    </option>

                </select>

            </div>

            <div className="acciones">

                <button
                    className="btn-guardar"
                    onClick={guardar}
                >
                    Guardar Permiso
                </button>

            </div>

        </div>

    );

}

export default FormPermiso;